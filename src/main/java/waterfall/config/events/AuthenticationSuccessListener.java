package waterfall.config.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import waterfall.service.LoginAttemptsService;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private LoginAttemptsService loginAttemptsService;
	
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
		loginAttemptsService.loginSucceeded(auth.getRemoteAddress());
	}
}
