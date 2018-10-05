package waterfall.service;

import java.util.List;

import waterfall.model.Role;

public interface RoleService {
	public void save(Role role);
	public void update(Role role);
	public void remove(Role role);
	public Role findById(Integer id);
	public List<Role> findAll();
}
