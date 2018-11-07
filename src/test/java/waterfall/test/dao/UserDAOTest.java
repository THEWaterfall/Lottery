package waterfall.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import waterfall.dao.RoleDAO;
import waterfall.dao.UserDAO;
import waterfall.model.Role;
import waterfall.model.User;
import waterfall.test.config.HibernateTestConfig;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@Transactional
public class UserDAOTest {
	
	@Resource
	private UserDAO userDAO;
	
	@Resource
	private RoleDAO roleDAO;
	
	private User user;
	
	@Before
	public void init() {
	    Role role = new Role("USER");
	    roleDAO.save(role);
	    
	    user = new User("User", "qwerty", "User@test.com", 1000, new HashSet<Role>(Arrays.asList(roleDAO.findById(1))), null);
	    userDAO.save(user);
	}
	
	@After 
	public void fini() {
		for(User user: userDAO.findAll())
			userDAO.remove(user);
		
		for(Role role: roleDAO.findAll())
			roleDAO.remove(role);
	}
	
	@Test
	public void findByIdTest() {
	    assertEquals(user, userDAO.findById(user.getId()));
	}
	
	@Test
	public void findByUsernameTest() {
		assertEquals("qwerty", userDAO.findByUsername("User").getPassword());
	}
	
	@Test 
	public void findAllTest() {
		User user2 = new User("Antony", "qwerty123", "Antony@test.com", 1000, new HashSet<Role>(Arrays.asList(roleDAO.findById(1))), null);
		userDAO.save(user2);
		 
		List<User> userList = userDAO.findAll();
		 
		assertEquals(user, userList.get(0));
		assertEquals(user2, userList.get(1));
		assertEquals(2, userList.size());
	}

	@Test
	public void saveUserTest() {
		User user2 = new User("Antony", "qwerty123", "Antony@test.com", 1000, new HashSet<Role>(Arrays.asList(roleDAO.findById(1))), null);
	    userDAO.save(user2);

	    assertEquals(user2, userDAO.findByUsername("Antony"));
	}

	@Test
	public void updateUserTest() {
	    user.setUsername("Superuser");
	    userDAO.update(user);
	    
	    assertEquals("Superuser", userDAO.findById(user.getId()).getUsername());
	}

	@Test
	public void removeUserTest() {
	    userDAO.remove(user);

	    assertNull(userDAO.findById(user.getId()));
	}
	
	@Test
	public void emptyUserTableTest() {
		List<User> userList = userDAO.findAll();
		for(User user: userList) {
			userDAO.remove(user);
		}
		
		assertTrue(userDAO.findAll().isEmpty());
	}
}
