package waterfall.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import org.springframework.web.context.request.WebRequest;

import waterfall.config.events.OnRegistrationEvent;
import waterfall.model.Role;
import waterfall.model.User;
import waterfall.model.VerificationToken;
import waterfall.service.UserService;
import waterfall.service.VerificationTokenService;

@Controller
public class RegistrationController {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserService userService;
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@RequestMapping(value = {"/register"}, method = RequestMethod.GET)
	public String showRegistration(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		
		return "RegistrationView";
	}
	
	@RequestMapping(value = {"/register"}, method = RequestMethod.POST)
	public String registration(ModelMap model, @Valid @ModelAttribute("user") User user, BindingResult result,  WebRequest request) {
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
		
		try {
			eventPublisher.publishEvent(new OnRegistrationEvent(user, request.getContextPath()));
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
		verificationToken.refresh(UUID.randomUUID().toString());
		
		try {
			eventPublisher.publishEvent(new OnRegistrationEvent(user, request.getContextPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/playground";
	}
	
	public void authWithoutPassword(User user){
	    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    
	    for(Role role: user.getRoles()) 
	    	authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getType()));

	    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
