package waterfall.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import waterfall.model.User;

@Repository
public class UserDAOImpl extends AbstractDAO<Integer, User> implements UserDAO {

	public UserDAOImpl() {
		setEntityClass(User.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User findByUsername(String username) {
		Query<User> query = getSession().createQuery("FROM User WHERE username=:username").setParameter("username", username);
		User user = query.uniqueResult();
		
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByEmail(String email) {
		Query<User> query = getSession().createQuery("FROM User WHERE email=:email").setParameter("email", email);
		User user = query.uniqueResult();
		
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllDisabled() {
		Query<User> query = getSession().createQuery("SELECT u FROM User u LEFT JOIN VerificationToken vt ON u.id = vt.user.id WHERE vt.id IS NULL AND u.enabled = 0");
		List<User> users = query.list();
	
		return users;
		
	}
}
