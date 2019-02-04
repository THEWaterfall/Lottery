package waterfall.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import waterfall.dao.LoginAttemptsDAO;
import waterfall.model.LoginAttempt;

@Service
@Transactional
public class LoginAttemptsServiceImpl implements LoginAttemptsService {

	@Autowired
	private LoginAttemptsDAO loginAttemptsDAO;
	
	private final static int MAX_ATTEMPTS = 10;
	
	@Override
	public void loginSucceeded(String key) {
		LoginAttempt loginAttempt = loginAttemptsDAO.findById(key);
		
		if (loginAttempt != null) 
			loginAttemptsDAO.remove(loginAttempt);
	}

	@Override
	public void loginFailed(String key) {
		LoginAttempt loginAttempt = loginAttemptsDAO.findById(key);
		
		if (loginAttempt == null) {
			loginAttempt = new LoginAttempt(key, 1);
		} else {
			loginAttempt.setAttempts(loginAttempt.getAttempts() + 1);
		}
		
		loginAttemptsDAO.saveOrUpdate(loginAttempt);
	}

	@Override
	public boolean isBlocked(String key) {
		LoginAttempt loginAttempt = loginAttemptsDAO.findById(key);
		if(loginAttempt == null)
			return false;
		else
			return loginAttempt.getAttempts() >= MAX_ATTEMPTS;
	}

	@Override
	public void removeExpiredSince(Date since) {
		loginAttemptsDAO.removeExpiredSince(since);
	}

}
