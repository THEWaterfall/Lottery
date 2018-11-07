package waterfall.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import waterfall.controller.LoginController;
import waterfall.test.config.HibernateTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@WebAppConfiguration
public class LoginControllerTest {
	
	@Autowired
	private LoginController loginController;
	
	private MockMvc mockMvc;
	
	@Before() 
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController)
				.build();
	}
	
	@Test
	public void loginTest() throws Throwable {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("LoginView"));
	}
	
	@Test
	public void loginUnavailableTest() throws Throwable {
		Authentication authToken = new TestingAuthenticationToken("User", "qwerty");
		SecurityContextHolder.getContext().setAuthentication(authToken);
	    
		mockMvc.perform(get("/login"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/playground"));
	}
}
