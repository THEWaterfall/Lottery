package waterfall.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import waterfall.model.Role;
import waterfall.model.User;
import waterfall.service.UserService;

public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		
		if(user == null) {
			logger.warn("User '{}' can't be found", username);
			throw new UsernameNotFoundException("User can't be found");
		} else {
			logger.info("User has been found: {}", user);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
		}
	}

	private List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role: user.getRoles())
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getType()));
		
		return authorities;
	}

}
