package waterfall.dao;

import org.springframework.stereotype.Repository;

import waterfall.model.Role;

@Repository
public class RoleDAOImpl extends AbstractDAO<Integer, Role> implements RoleDAO {

	public RoleDAOImpl() {
		setEntityClass(Role.class);
	}
}
