package waterfall.dao;

import java.util.List;

import waterfall.model.Role;

public interface RoleDAO {
	public void save(Role role);
	public void update(Role role);
	public void remove(Role role);
	public Role findById(Integer id);
	public List<Role> findAll();	
}
