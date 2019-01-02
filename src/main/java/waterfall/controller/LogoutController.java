package waterfall.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {
	
	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
	public String logout() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			SecurityContextHolder.getContext().setAuthentication(null);
			LotteryController.setMachine(null);
			LotteryController.setPlayer(null);
			LotteryController.setUser(null);
		}
		
		return "redirect:/login?logout";
	}
}
