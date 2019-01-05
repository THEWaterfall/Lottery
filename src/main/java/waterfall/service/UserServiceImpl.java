package waterfall.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import waterfall.dao.UserDAO;
import waterfall.model.Role;
import waterfall.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.save(user);
	}

	@Override
	public void update(User user) {
		if(userDAO.findById(user.getId()).getPassword() != user.getPassword()) 
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.merge(user);
//		userDAO.update(user);
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
	public User findByEmail(String username) {
		return userDAO.findByEmail(username);
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

		for(User user: userList) {
			for(Role role: user.getRoles()) {
				if(role.getType().equals(("ROOT"))) {
					break;
				}
			}
			top.add(user);
		}
		
		return top;
	}

	@Override
	public boolean isUsernameUnique(User user) {
		User possibleUser = userDAO.findByUsername(user.getUsername());
		
		if((possibleUser == null) || (possibleUser != null && possibleUser.getId() == user.getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isEmailUnique(User user) {
		User possibleUser = userDAO.findByEmail(user.getEmail());
		
		if((possibleUser == null) || (possibleUser != null && possibleUser.getId() == user.getId())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void removeAll(List<User> userList) {
			for(User user: userList)
				userDAO.remove(user);
	}
	
	@Override
	public void removeAllDisabled() {
		removeAll(userDAO.findAllDisabled());
	}
	
	
	
}
