package waterfall.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import waterfall.dao.RoleDAO;
import waterfall.dao.UserDAO;
import waterfall.model.Role;
import waterfall.model.User;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public void save(User user) {
		userDAO.save(user);
	}

	@Override
	public void update(User user) {
		userDAO.update(user);
	}

	@Override
	public void remove(User user) {
		userDAO.remove(user);
	}

	@Override
	public User findById(Integer id) {
		return userDAO.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	@Override
	public List<User> findTop() {
		List<User> userList = userDAO.findAll();
		userList.sort(new Comparator<User>() 
		{
			@Override
			public int compare(User user1, User user2) {
				return user2.getCredits()-user1.getCredits();
			}
		});
		
		List<User> top = new ArrayList<User>();
		Role root = roleDAO.findById(1);
		
		for(User user: userList) {
			if(!user.getRoles().contains(root))
				top.add(user);
		}
		
		return top;
	}

}
