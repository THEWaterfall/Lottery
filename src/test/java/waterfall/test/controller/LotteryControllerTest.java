package waterfall.test.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import powerball.Machine;
import powerball.Player;
import powerball.Ticket;
import waterfall.controller.LotteryController;
import waterfall.model.Role;
import waterfall.model.User;
import waterfall.service.RoleService;
import waterfall.service.UserService;
import waterfall.test.config.HibernateTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateTestConfig.class)
@WebAppConfiguration
public class LotteryControllerTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	private MockMvc mockMvc;
	
	@Autowired
	private LotteryController lotteryController;
	
	private Machine machine;
	private Player player;
	private Role userRole;
	private User user;
	
	@Before() 
	public void init() {
		userRole = new Role("USER");
		roleService.save(userRole);
		
		machine = new Machine();
		player = new Player("User", 2000);
		user = new User("User", "qwerty", "User@user.com", 2000, new HashSet<Role>(Arrays.asList(userRole)), null);
		userService.save(user);
		
		lotteryController = new LotteryController(user, player, machine, userService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
					.build();
	}
	
	@After() 
	public void fini() {
		for(User user: userService.findAll())
			userService.remove(user);
		
		for(Role role: roleService.findAll())
			roleService.remove(role);
	}
	
	@Test
	public void showLotteryPlaygroundTest() throws Throwable {
		mockMvc.perform(get("/playground"))
				.andExpect(view().name("LotteryPlayGroundView"));
	}
	
	@Test
	public void showAddTicketView() throws Throwable {
		//ReflectionTestUtils.setField(lotteryController, "player", player);
		mockMvc.perform(get("/playground/ticket"))
				.andExpect(status().isOk())
				.andExpect(view().name("AddTicketView"));
	}
	
	@Test
	public void AddTicketViewNotEnoughCreditsTest() throws Throwable {
		player.setCredits(1);
		mockMvc.perform(get("/playground/ticket"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("msg", "You can't buy a ticket. You don't have enough credits"))
				.andExpect(view().name("LotteryPlayGroundView"));
	}
	
	@Test
	public void performAddTicketWithQuickpickTest() throws Throwable {
		mockMvc.perform(post("/playground/ticket")
					.param("whiteBallsQuickPick", "true")
					.param("redBallsQuickPick", "true"))
				.andExpect(status().is3xxRedirection())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("redirect:/playground"));
		
		assertEquals(1, player.getTickets().size());
	}
	
	@Test
	public void performAddTicketWithManualChoosingTest() throws Throwable {
		mockMvc.perform(post("/playground/ticket")
					.param("chosenWhiteBalls", "1,2,3,4,5")
					.param("chosenRedBalls", "10"))
				.andExpect(status().is3xxRedirection())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("redirect:/playground"));
		
		assertEquals(1, player.getTickets().size());
	}
	
	@Test
	public void performAddTicketWithQuickpickAndManualChoosingTest() throws Throwable {
		mockMvc.perform(post("/playground/ticket")
					.param("whiteBallsQuickPick", "true")
					.param("chosenRedBalls", "5"))
				.andExpect(status().is3xxRedirection())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("redirect:/playground"));
		
		assertEquals(1, player.getTickets().size());
	}
	
	@Test 
	public void performAddTicketWithFieldErrorsTest() throws Throwable {
		mockMvc.perform(post("/playground/ticket")
					.param("chosenWhiteBalls", "1,2,3,4,5,6")
					.param("chosenRedBalls", "10, 11"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("ticket", "chosenWhiteBalls", "chosenRedBalls"))
				.andExpect(view().name("AddTicketView"));
	}
	
	@Test
	public void performAddMoreTicketsTest() throws Throwable {
		mockMvc.perform(post("/playground/moretickets")
					.param("amount", "5"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/playground"));
		
		assertEquals(5, player.getTickets().size());
	}
	
	@Test
	public void performAddMoreTicketsNotEnoughCreditsTest() throws Throwable {
		mockMvc.perform(post("/playground/moretickets")
					.param("amount", "2000"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("msg", "You can't buy this amount of tickets. You don't have enough credits"))
				.andExpect(view().name("LotteryPlayGroundView"));
		
		assertEquals(0, player.getTickets().size());
	}
	
	@Test
	public void performPlayLotteryTest() throws Throwable {
		Ticket ticket = new Ticket(true, true);
		player.addTicket(ticket);
	
		mockMvc.perform(get("/playground/play"))
				.andExpect(status().isOk())
				.andExpect(view().name("LotteryResultsView"));
	}
	
	@Test
	public void performPlayLotteryWithErrorTest() throws Throwable {
		mockMvc.perform(get("/playground/play"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("msg", "You have no tickets to play"))
				.andExpect(view().name("LotteryPlayGroundView"));
	}
	
	@Test
	public void showLadderTest() throws Throwable {
		mockMvc.perform(get("/top"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("users", userService.findTop()))
				.andExpect(view().name("TopPlayersView"));
	}
	
}
