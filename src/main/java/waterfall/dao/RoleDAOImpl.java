package waterfall.dao;

import waterfall.model.Role;

public class RoleDAOImpl extends AbstractDAO<Role> implements RoleDAO {

	public RoleDAOImpl() {
		setEntityClass(Role.class);
	}
}
