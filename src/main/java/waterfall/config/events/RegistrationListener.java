package waterfall.config.events;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import waterfall.model.User;
import waterfall.service.VerificationTokenService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationEvent> {
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void onApplicationEvent(OnRegistrationEvent event) {
		confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		tokenService.createVerificationToken(user, token);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setSubject("Registration Confirmation");
		message.setText("Confirm your email: " + "http://localhost:8080" + event.getUrl() + "/register/confirm?token=" + token);
		mailSender.send(message);
	}

}
