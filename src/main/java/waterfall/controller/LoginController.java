package waterfall.controller;

import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String login() {
		if(isAnonymous()) {
			return "LoginView";
		} else {
			return "redirect:/playground";
		}
	}

	private boolean isAnonymous() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		return new AuthenticationTrustResolverImpl().isAnonymous(auth);
	}
}
