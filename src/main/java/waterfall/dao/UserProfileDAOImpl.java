package waterfall.dao;

import waterfall.model.UserProfile;

public class UserProfileDAOImpl extends AbstractDAO<UserProfile> implements UserProfileDAO {
	
	public UserProfileDAOImpl() {
		setEntityClass(UserProfile.class);
	}
}
