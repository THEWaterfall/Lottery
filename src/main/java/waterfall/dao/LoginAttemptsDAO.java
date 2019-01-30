package waterfall.dao;

import java.util.Date;
import java.util.List;

import waterfall.model.LoginAttempt;

public interface LoginAttemptsDAO {
	public LoginAttempt findById(String key);
	public List<LoginAttempt> findAll();
	public void remove(LoginAttempt loginAttempt);
	public void save(LoginAttempt loginAttempt);
	public void update(LoginAttempt loginAttempt);
	public void removeExpiredSince(Date since);
	public void saveOrUpdate(LoginAttempt loginAttempt);
}
