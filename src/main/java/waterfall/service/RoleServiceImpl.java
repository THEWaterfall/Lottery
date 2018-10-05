package waterfall.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import waterfall.dao.RoleDAO;
import waterfall.model.Role;

@Service
@Transactional
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
