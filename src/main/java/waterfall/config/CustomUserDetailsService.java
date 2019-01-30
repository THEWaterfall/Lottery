package waterfall.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import waterfall.model.Role;
import waterfall.model.User;
import waterfall.service.LoginAttemptsService;
import waterfall.service.UserService;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginAttemptsService loginAttemptsService;
	
	@Autowired
	private HttpServletRequest request;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (loginAttemptsService.isBlocked(getClientIP())) {
			throw new RuntimeException("Blocked");
	    }
		
		User user = userService.findByUsername(username);
		
		if(user == null) {
			logger.warn("User '{}' can't be found", username);
			throw new UsernameNotFoundException("User can't be found");
		} else {
			logger.info("User has been found: {}", user);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user));
		}
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		
	    if (xfHeader == null){
	        return request.getRemoteAddr();
	    }
	    
	    return xfHeader.split(",")[0];
	}

	private List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role: user.getRoles())
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getType()));
		
		return authorities;
	}

}
