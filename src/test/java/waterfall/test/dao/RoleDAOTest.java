package waterfall.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import waterfall.dao.RoleDAO;
import waterfall.model.Role;
import waterfall.test.config.HibernateTestConfig;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@Transactional
public class RoleDAOTest {

	@Autowired
	private RoleDAO roleDAO;
	
	private Role role;
	
	@Before
	public void init() {
		role = new Role();
		role.setType("USER");
		roleDAO.save(role);
	}
	
	@After
	public void finilize() {
		roleDAO.remove(role);
	}
	
	@Test
	public void findByIdTest() {
		assertEquals(role, roleDAO.findById(role.getId()));
	}
	
	@Test
	public void findAllTest() {
		Role role2 = new Role();
		role2.setType("ROOT");
		roleDAO.save(role2);
		
		List<Role> roleList = roleDAO.findAll();
		
		assertEquals(role2, roleList.get(1));
		assertEquals(2, roleList.size());
	}
	
	@Test
	public void addTest() {
		Role role2 = new Role();
		role2.setType("ROOT");
		roleDAO.save(role2);
		
		assertEquals(role2, roleDAO.findById(role2.getId()));
	}
	
	@Test
	public void updateTest() {
		role.setType("MODER");
		roleDAO.update(role);
		
		assertEquals("MODER", roleDAO.findById(role.getId()).getType());
	}
	
	@Test
	public void removeTest() {
		 roleDAO.remove(role);
		 
		 assertNull(roleDAO.findById(role.getId()));
	}
	
	@Test
	public void emptyRoleTable() {
		List<Role> roleList = roleDAO.findAll();
		for(Role role: roleList) {
			roleDAO.remove(role);
		}
		
		assertTrue(roleDAO.findAll().isEmpty());
	}
}
