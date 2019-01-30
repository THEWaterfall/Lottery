package waterfall.dao;

import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import waterfall.model.LoginAttempt;

@Repository
public class LoginAttemptsDAOImpl extends AbstractDAO<String, LoginAttempt> implements LoginAttemptsDAO{

	public LoginAttemptsDAOImpl() {
		setEntityClass(LoginAttempt.class);
	}

	@Override
	public void removeExpiredSince(Date since) {
		Query query = getSession().createQuery("DELETE FROM LoginAttempt WHERE expiryDate < :date")
													.setParameter("date", since);
		query.executeUpdate();
	}

	@Override
	public void saveOrUpdate(LoginAttempt loginAttempt) {
		getSession().saveOrUpdate(loginAttempt);
	}
}
