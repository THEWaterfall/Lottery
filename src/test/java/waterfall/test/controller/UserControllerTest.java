package waterfall.test.controller;

import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import waterfall.config.StringToRoleConverter;
import waterfall.controller.UserController;
import waterfall.model.Role;
import waterfall.model.User;
import waterfall.service.RoleService;
import waterfall.service.UserService;
import waterfall.test.config.HibernateTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@WebAppConfiguration
public class UserControllerTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserController userController;
			
	@Autowired
	private StringToRoleConverter stringToRoleConverter;
	
	private MockMvc mockMvc;
	
	private Role userRole;
	private User user;
	
	@Before
	public void init() {
		FormattingConversionService cs = new FormattingConversionService();
		cs.addConverter(stringToRoleConverter);
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setConversionService(cs)
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
	public void showUserListTest() throws Throwable {
		List<User> users = Arrays.asList(user);
		
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("users", users))
				.andExpect(view().name("UserListView"));
	}
	
	@Test
	public void showAddUserTest() throws Throwable {
		mockMvc.perform(get("/users/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("AddUserView"));
	}
	
	@Test
	public void performAddUserTest() throws Throwable {
		mockMvc.perform(post("/users/add")
					.param("username", "User2")
					.param("password", "qwerty")
					.param("email", "User@user.com")
					.param("credits", "2000")
					.param("roles", String.valueOf(userRole.getId())))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/users"));
	}
	
	@Test
	public void performAddUserWithExistingUsernameTest() throws Throwable {
		mockMvc.perform(post("/users/add")
					.param("username", "User")
					.param("password", "qwerty")
					.param("email", "User@user.com")
					.param("credits", "2000")
					.param("roles", String.valueOf(userRole.getId())))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("user", "username"))
				.andExpect(view().name("AddUserView"));
	}
	
	@Test
	public void performUpdateUserWithExistingUsernameTest() throws Throwable {
		
		mockMvc.perform(post("/users/edit/{id}", user.getId())
					.param("username", "User")
					.param("password", "qwerty")
					.param("email", "User@user.com")
					.param("credits", "2000")
					.param("roles", String.valueOf(userRole.getId()))
				)
				.andExpect(model().hasNoErrors())
				.andExpect(model().attribute("user", user));
	}
	
	@Test
	public void perfomRemoveUserTest() throws Throwable {
		mockMvc.perform(get("/users/remove/{id}", user.getId()))
			 	.andExpect(status().is3xxRedirection())
			 	.andExpect(view().name("redirect:/users"));
		
		assertNull(userService.findById(user.getId()));
	}
	
	@Test
	public void showUserTest() throws Throwable {
		mockMvc.perform(get("/users/profile/{id}", user.getId()))
				.andExpect(status().isOk())
				.andExpect(model().attribute("user", user))
				.andExpect(view().name("ShowUserView"));
	}
}
