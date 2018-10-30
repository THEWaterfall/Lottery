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
	    Role role = new Role();
	    role.setId(1);
	    role.setType("User");
	    roleDAO.save(role);
	    
	    user = new User();
	    user.setCredits(1000);
	    user.setEmail("User@test.com");
	    user.setUsername("User");
	    user.setPassword("qwerty");
	    user.setRoles(new HashSet<Role>(Arrays.asList(roleDAO.findById(1))));
	    userDAO.save(user);
	}
	
	@After 
	public void finilize() {
		userDAO.remove(user);
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
		 User user2 = new User();
		 user2.setCredits(1000);
		 user2.setEmail("Antony@test.com");
		 user2.setPassword("qwerty123");
		 user2.setUsername("Antony");
		 user2.setRoles(new HashSet<Role>(Arrays.asList(roleDAO.findById(1))));
		 userDAO.save(user2);
		 
		 List<User> userList = userDAO.findAll();
		 
		 assertEquals(user, userList.get(0));
		 assertEquals(user2, userList.get(1));
		 assertEquals(2, userList.size());
	}

	@Test
	public void saveUserTest() {
	    User user2 = new User();
	    user2.setCredits(1000);
	    user2.setEmail("Antony@test.com");
	    user2.setPassword("qwerty123");
	    user2.setUsername("Antony");
	    user2.setRoles(new HashSet<Role>(Arrays.asList(roleDAO.findById(1))));
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
