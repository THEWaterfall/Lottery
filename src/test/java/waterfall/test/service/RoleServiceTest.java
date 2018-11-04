package waterfall.test.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import waterfall.model.Role;
import waterfall.service.RoleService;
import waterfall.test.config.HibernateTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@WebAppConfiguration
public class RoleServiceTest {

	@Autowired
	private RoleService roleService;
	
	private Role rootRole;
	
	@Before
	public void init() {
		rootRole = new Role("ROOT");	
		roleService.save(rootRole);
	}

	@After
	public void fini() {
		for(Role role: roleService.findAll())
			roleService.remove(role);
	}
	
	@Test
	public void findByIdTest() {
		assertEquals(rootRole, roleService.findById(rootRole.getId()));
	}
	
	@Test
	public void findAllTest() {
		Role role1 = new Role("USER");
		roleService.save(role1);
		Role role2 = new Role("MODER");
		roleService.save(role2);
		
		List<Role> roleList = roleService.findAll();
		
		assertEquals(3, roleList.size());
		assertEquals(role2, roleList.get(2));
	}
	
	@Test
	public void saveTest() {
		Role role = new Role("USER");
		roleService.save(role);
		
		assertEquals(role, roleService.findById(role.getId()));
	}
	
	@Test
	public void updateTest() {
		rootRole.setType("ROLE_ROOT");
		roleService.update(rootRole);
		
		assertEquals("ROLE_ROOT", roleService.findById(rootRole.getId()).getType());
	}
	
	@Test
	public void removeTest() {
		roleService.remove(rootRole);
		
		assertNull(roleService.findById(rootRole.getId()));
	}
	
	@Test
	public void emptyRoleTable() {
		for(Role role: roleService.findAll()) {
			roleService.remove(role);
		}
		
		assertTrue(roleService.findAll().isEmpty());
	}
	
}
