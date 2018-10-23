package waterfall.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import powerball.InvalidTicketException;
import powerball.LackDepositException;
import powerball.Machine;
import powerball.Player;
import powerball.Ticket;
import waterfall.model.User;
import waterfall.service.UserService;

@Controller
public class LotteryController {

	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(LotteryController.class);
	
	private Player player;
	private Machine machine;
	
	@RequestMapping(value = {"/", "/playground"}, method = RequestMethod.GET)
	public String showLotteryPlayGround(ModelMap model) {
		if(player == null || !player.getNickName().equals(getUser().getUsername()))
			player = new Player(getUser().getUsername(), getUser().getCredits());
		if(machine == null)
			machine = new Machine();
		
		return "LotteryPlayGroundView";
	}
	
	@RequestMapping(value = {"/playground/ticket"}, method = RequestMethod.GET)
	public String showAddTicket(ModelMap model) {
		if (player.getCredits() < 2) {
			model.addAttribute("msg", "You can't buy a ticket. You don't have enough credits");
			
			return "LotteryPlayGroundView";
		}
		
		Ticket ticket = new Ticket();
		model.addAttribute("ticket", ticket);
		model.addAttribute("whiteBalls", getWhiteBalls());
		model.addAttribute("redBalls", getRedBalls());
	
		return "AddTicketView";
	}
	
	@RequestMapping(value = {"/playground/ticket"}, method = RequestMethod.POST)
	public String addTicket(ModelMap model, @Valid @ModelAttribute("ticket") Ticket ticket, BindingResult result) {
		
		boolean whiteBallsQuickPick = ticket.isWhiteBallsQuickPick();
		boolean redBallsQuickPick = ticket.isRedBallsQuickPick();
		
		if(whiteBallsQuickPick && !redBallsQuickPick) {
			ticket.quickPickWhiteBalls();
		} else if(!whiteBallsQuickPick && redBallsQuickPick) {
			ticket.quickPickRedBalls();
		} else if (whiteBallsQuickPick && redBallsQuickPick) {
			ticket.quickPickRedBalls();
			ticket.quickPickWhiteBalls();
		}
		
		if(ticket.getChosenWhiteBalls().size() != 5) {
			FieldError whiteBallsError = new FieldError("ticket", "chosenWhiteBalls", "there must be 5 balls");
			result.addError(whiteBallsError);
		}
		
		if(ticket.getChosenRedBalls().size() != 1) {
			FieldError redBallsError = new FieldError("ticket", "chosenRedBalls", "there must be 1 ball");
			result.addError(redBallsError);
		}
		
		if(result.hasErrors()) {
			ticket.getChosenRedBalls().clear();
			ticket.getChosenWhiteBalls().clear();
			
			model.addAttribute("whiteBalls", getWhiteBalls());
			model.addAttribute("redBalls", getRedBalls());
			
			return "AddTicketView";
		}
		
		try {
			player.addTicket(ticket);
		} catch (LackDepositException e) {
			e.printStackTrace();
		}
		
		logger.info("{} added a ticket: {}", getUser().getUsername(), ticket);
		
		return "redirect:/playground";
	}
	
	@RequestMapping(value = {"/playground/moretickets"}, method = RequestMethod.POST)
	public String addMoreTickets(ModelMap model, @RequestParam("amount") int amount) {
		if(player.getCredits() < amount*2) {
			model.addAttribute("msg", "You can't buy this amount of tickets. You don't have enough credits");
			
			return "LotteryPlayGroundView";
		}
		
		for(int i = 0; i < amount; i++) {
			Ticket ticket = new Ticket(true, true);
			
			try {
				player.addTicket(ticket);
			} catch (LackDepositException e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/playground";
	}
	
	@RequestMapping(value = {"/playground/play"}, method = RequestMethod.GET)
	public String playLottery(ModelMap model) {
		if(player.getTickets().size() == 0) {
			model.addAttribute("msg", "You have no tickets to play");
			return "LotteryPlayGroundView";
		}
		
		User user = getUser();
		user.setCredits(player.getCredits());
		
		try {
			machine.registerTicket(player);
		} catch (InvalidTicketException e) {
			e.printStackTrace();
		}
		
		Map<Player, List<Ticket>> winners = machine.draw();
		
		int totalWinningPrize = 0;
		if(winners.size() > 0) {
			for(Ticket ticket: winners.get(player)) {
				if(ticket.getWinningPrize()>0) {
					logger.info("{} won {} credits with ticket: {}", user.getUsername(), ticket.getWinningPrize(), ticket);
					totalWinningPrize += ticket.getWinningPrize();
				}
			}
			model.addAttribute("winningPrize", totalWinningPrize);
			model.addAttribute("won", true);
		} else {
			model.addAttribute("lost", true);
		}
		
		player.setCredits(user.getCredits() + totalWinningPrize);
		user.setCredits(user.getCredits() + totalWinningPrize);
		userService.update(user);
		
		return "LotteryResultsView";
	}
	
	@RequestMapping(value = {"/top"}, method = RequestMethod.GET)
	public String showTop(ModelMap model) {
		List<User> users = userService.findTop();
		model.addAttribute("users", users);
		
		return "TopPlayersView";
	}
	
	@ModelAttribute("username")
	private String getUsername() {
		return getUser().getUsername();
	}
	
	@ModelAttribute("player")
	private Player getPlayer() {
		return this.player;
	}
	
	private User getUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username);
		
		return user;
	}
	
	private List<Integer> getWhiteBalls() {
		List<Integer> whiteBalls = new ArrayList<Integer>(69);
		for(int i = 1; i <= 69; i++) 
			whiteBalls.add(i);
		
		return whiteBalls;
	}
	
	private List<Integer> getRedBalls() {
		List<Integer> redBalls = new ArrayList<Integer>(26);
		for(int i = 1; i <= 26; i++) 
			redBalls.add(i);
		
		return redBalls;
	}
}
