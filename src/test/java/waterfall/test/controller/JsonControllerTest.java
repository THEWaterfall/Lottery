package waterfall.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import waterfall.controller.JsonController;
import waterfall.model.Role;
import waterfall.model.User;
import waterfall.service.RoleService;
import waterfall.service.UserService;
import waterfall.test.config.HibernateTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@WebAppConfiguration
public class JsonControllerTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private JsonController jsonController;
	
	private MockMvc mockMvc;
	private User user;
	private Role userRole;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(jsonController)
				.build();
		
		userRole = new Role("USER");
		roleService.save(userRole);
		
		user = new User("User", "qwerty", "User@user.com", 2000, new HashSet<Role>(Arrays.asList(userRole)), null);
		userService.save(user);
	}
	
	@After
	public void fini() {
		for(User user: userService.findAll())
			userService.remove(user);
		
		for(Role role: roleService.findAll())
			roleService.remove(role);
	}
	
	@Test
	public void performSaveToJsonTest() throws Throwable {
		mockMvc.perform(get("/users/save/{id}", user.getId()))	
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/users"));
	}
}
