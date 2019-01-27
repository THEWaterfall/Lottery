package waterfall.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import waterfall.config.events.OnRegistrationEvent;
import waterfall.model.PasswordResetToken;
import waterfall.model.ReCaptchaResponse;
import waterfall.model.Role;
import waterfall.model.User;
import waterfall.model.VerificationToken;
import waterfall.service.PasswordResetTokenService;
import waterfall.service.UserService;
import waterfall.service.VerificationTokenService;

@Controller
public class RegistrationController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserService userService;
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping(value = {"/register"}, method = RequestMethod.GET)
	public String showRegistration(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("sitekey", environment.getProperty("recaptcha.sitekey"));
		
		return "RegistrationView";
	}
	
	@RequestMapping(value = {"/register"}, method = RequestMethod.POST)
	public String registration(ModelMap model, @Valid @ModelAttribute("user") User user, BindingResult result,  WebRequest request) {
		String captchaResponse = request.getParameter("g-recaptcha-response");
		
		if(!processCaptcha(captchaResponse)) {
			String captchaError = "Error captcha";
			model.addAttribute(captchaError);
			return "RegistrationView";
		}
		
		if(!userService.isEmailUnique(user)) {
			FieldError fieldError = new FieldError("user", "email", "Email is already taken");
			result.addError(fieldError);
		}
		 
		if(!userService.isUsernameUnique(user)) {
			FieldError fieldError = new FieldError("user", "username", "username is already taken");
			result.addError(fieldError);
		}
		
		if(result.hasErrors()) {
			return "RegistrationView";
		}
		
		userService.save(user);
		
		VerificationToken verificationToken = tokenService.createVerificationToken(user, UUID.randomUUID().toString());
		
		try {
			eventPublisher.publishEvent(new OnRegistrationEvent(user, request.getContextPath(), verificationToken));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//to implement
		//return "SuccessRegistrationView";
		return "redirect:/login";
	}
	
	@RequestMapping(value = {"/register/confirm"}, method = RequestMethod.GET)
	public String registerConfirm(ModelMap model, @RequestParam("token") String token) {
		VerificationToken verificationToken = tokenService.findByToken(token);

		//check if token is valid
		if(verificationToken == null) {
			//redirect to page saying about wrong token
		}
		
		User user = verificationToken.getUser();
		
		Calendar calendar = Calendar.getInstance();
		if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
			model.addAttribute("token", token);
			return "redirect:/login?error";
		}
		
		user.setEnabled(true);
		userService.update(user);
		tokenService.remove(verificationToken);
		
		authWithoutPassword(user);
		
		return "redirect:/login";
	}
	
	@RequestMapping(value = {"/register/resend"}, method = RequestMethod.GET)
	public String resendToken(ModelMap model) {
		return "ResendTokenView";
	}
	
	@RequestMapping(value = {"/register/resend"}, method = RequestMethod.POST)
	public String performResendToken(ModelMap model, @RequestParam("email") String email, WebRequest request) {
		User user = userService.findByEmail(email);
		
		if(user == null) {
			model.addAttribute("errorMsg", "Such email is not registered");
			return "ResendTokenView";
		}
		
		VerificationToken verificationToken = tokenService.findByUser(user);
		
		if(verificationToken == null) {
			verificationToken = tokenService.createVerificationToken(user, UUID.randomUUID().toString());
		} else {
			verificationToken.refresh(UUID.randomUUID().toString());
			tokenService.update(verificationToken);
		}
		
		try {
			eventPublisher.publishEvent(new OnRegistrationEvent(user, request.getContextPath(), verificationToken));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/playground";
	}
	
	@RequestMapping(value = {"/password/reset"}, method = RequestMethod.GET) 
	public String resetPassword() {
		return "ResetPasswordView";
	}
	
	@RequestMapping(value = {"/password/reset"}, method = RequestMethod.POST) 
	public String performResetPassword(ModelMap model, @RequestParam("email") String email, WebRequest request) {
		User user = userService.findByEmail(email);
		
		if(user == null) {
			model.addAttribute("errorMsg", "Such email is not registered");
			return "ResetPasswordView";
		}
		
		PasswordResetToken resetToken = passwordResetTokenService.findByUser(user);
		String token = UUID.randomUUID().toString();
		
		if(resetToken == null) {
			passwordResetTokenService.createPasswordResetToken(user, token);
		} else {
			resetToken.refresh(token);
			passwordResetTokenService.update(resetToken);
		}
		
		mailSender.send(constructResetTokenEmail(request.getContextPath(), token, user));
		
		return "redirect:/login";
	}
	
	@RequestMapping(value = {"/password"}, method = RequestMethod.GET)
	public String changePassword(ModelMap model, @RequestParam("id") Integer id, @RequestParam("token") String token) {
		User user = userService.findById(id);
		
		if(passwordResetTokenService.isResetPasswordTokenValid(id, token)) {
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
			SecurityContextHolder.getContext().setAuthentication(auth);
			return "redirect:/password/update";
		} else {
			// reset password token is invalid
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = {"/password/update"}, method = {RequestMethod.GET})
	public String updatePassword() {
		return "UpdatePasswordView";
	}
	
	@RequestMapping(value = {"/password/update"}, method = {RequestMethod.POST})
	public String performUpdatePassword(@RequestParam("password") String password) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		user.setPassword(password);
		userService.update(user);
		
		return "redirect:/logout";
	}
	
	private SimpleMailMessage constructResetTokenEmail(
		String contextPath, String token, User user) {
		String url = contextPath + "/password?id=" + user.getId() + "&token=" + token;
		String message = "Visit the following link to reset your password:";
		return constructEmail("Reset Password", message + " \r\n" + url, user);
	}
			 
	private SimpleMailMessage constructEmail(String subject, String body, User user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		
		return email;
	}

	private void authWithoutPassword(User user){
	    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    
	    for(Role role: user.getRoles()) 
	    	authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getType()));

	    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private boolean processCaptcha(String response) {
		if (response == null)
			return false;
		
		String secretkey = environment.getProperty("recaptcha.secretkey");
		URI verifyUri = URI.create(String.format(
		          "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s", secretkey, response));
		ReCaptchaResponse captcha = restTemplate.exchange(verifyUri, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();
		
		if(!captcha.isSuccess())
			return false;
		
		return true;
	}
	
}
