package waterfall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import waterfall.dao.RoleDAO;
import waterfall.model.Role;

public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public void save(Role role) {
		roleDAO.save(role);
	}

	@Override
	public void update(Role role) {
		roleDAO.update(role);
	}

	@Override
	public void remove(Role role) {
		roleDAO.remove(role);
	}

	@Override
	public Role findById(Integer id) {
		return roleDAO.findById(id);
	}

	@Override
	public List<Role> findAll() {
		return roleDAO.findAll();
	}

}
