package waterfall.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import waterfall.model.User;

@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

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
}
