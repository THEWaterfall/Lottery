package waterfall.dao;

import org.springframework.stereotype.Repository;

import waterfall.model.UserProfile;

@Repository
public class UserProfileDAOImpl extends AbstractDAO<UserProfile> implements UserProfileDAO {
	
	public UserProfileDAOImpl() {
		setEntityClass(UserProfile.class);
	}
}
