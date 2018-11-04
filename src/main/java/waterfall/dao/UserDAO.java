package waterfall.dao;

import java.util.List;

import waterfall.model.User;

public interface UserDAO {
	public void merge(User user);
	public void save(User user);
	public void update(User user);
	public void remove(User user);
	public User findById(Integer id);
	public List<User> findAll();
	public User findByUsername(String username);
}
