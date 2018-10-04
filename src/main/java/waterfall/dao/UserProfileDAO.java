package waterfall.dao;

import java.util.List;

import waterfall.model.UserProfile;

public interface UserProfileDAO {
	public void save(UserProfile userProfile);
	public void update(UserProfile userProfile);
	public void remove(UserProfile userProfile);
	public UserProfile findById(Integer id);
	public List<UserProfile> findAll();
}
