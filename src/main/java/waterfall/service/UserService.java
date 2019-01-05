package waterfall.service;

import java.util.List;

import waterfall.model.User;

public interface UserService {
	public void save(User user);
	public void update(User user);
	public void remove(User user);
	public User findById(Integer id);
	public List<User> findAll();
	public User findByUsername(String username);
	public boolean isUsernameUnique(User user);
	public boolean isEmailUnique(User user);
	public List<User> findTop();
	public User findByEmail(String username);
	public void removeAllDisabled();
	public void removeAll(List<User> userList);
}
