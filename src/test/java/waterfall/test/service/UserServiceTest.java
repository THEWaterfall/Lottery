package waterfall.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

import waterfall.model.Role;
import waterfall.model.User;
import waterfall.service.RoleService;
import waterfall.service.UserService;
import waterfall.test.config.HibernateTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@WebAppConfiguration
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	private User user;
	
	private Role rootRole;
	private Role userRole;
	
	@Before
	public void init() {
		rootRole = new Role("ROOT");
		roleService.save(rootRole);
		
		userRole = new Role("USER");
		roleService.save(userRole);
		
		user = new User("User", "qwerty", "User@test.com", 2000, new HashSet<Role>(Arrays.asList(userRole)), null);
		userService.save(user);
	}
	
	@After 
	public void fini() {
		for(User user: userService.findAll()) 
			userService.remove(user);
	}
	
	@Test
	public void findTopTest() {
		User user2 = new User("User2", "qwerty2", "User2@test.com", 100000, new HashSet<Role>(Arrays.asList(rootRole)), null);
		userService.save(user2);
		
		User user3 = new User("User3", "qwerty3", "User3@test.com", 5000, new HashSet<Role>(Arrays.asList(userRole)), null);
		userService.save(user3);
		
		assertEquals("User3", userService.findTop().get(0).getUsername());
		assertEquals(2, userService.findTop().size());
	}
	
	@Test
	public void isUsernameUniqueTest() {
		User user2 = new User("User", "qwerty312", "User1@test.com", 1400, new HashSet<Role>(Arrays.asList(userRole)), null);

		assertFalse(userService.isUsernameUnique(user2));
	}
	
	@Test
	public void findByIdTest() {
		assertEquals(user, userService.findById(user.getId()));
	}
	
	@Test
	public void findByUsernameTest() {
		assertEquals(user, userService.findByUsername(user.getUsername()));
	}
	
	@Test 
	public void findAllTest() {
		assertEquals(1, userService.findAll().size());
		assertEquals(user, userService.findAll().get(0));
	}
	
	@Test
	public void saveTest() {
		User user2 = new User("User", "qwerty312", "User1@test.com", 1400, new HashSet<Role>(Arrays.asList(userRole)), null);
		userService.save(user2);
		
		assertEquals(user2, userService.findById(user2.getId()));
	}
	
	@Test 
	public void saveAndEncryptPassTest() {
		User user2 = new User("User", "qwerty312", "User1@test.com", 1400, new HashSet<Role>(Arrays.asList(userRole)), null);
		userService.save(user2);
		
		assertNotEquals("qwerty321", userService.findById(user2.getId()).getUsername());
	}
	
	@Test 
	public void updateTest() {
		user.setUsername("Cool guy");
		userService.update(user);
		
		assertEquals("Cool guy", userService.findById(user.getId()).getUsername());
	}
	
	@Test
	public void updateAndEncryptPassTest() {
		user.setPassword("password");
		userService.update(user);
		
		assertNotEquals("password", userService.findById(user.getId()).getPassword());
	}
	
	@Test
	public void removeTest() {
		userService.remove(user);
		
		assertTrue(userService.findAll().isEmpty());
		assertNull(userService.findById(user.getId()));
	}
}
