package waterfall.service;

import java.util.Date;

public interface LoginAttemptsService {
	public void loginSucceeded(String key);
	public void loginFailed(String key);
	public boolean isBlocked(String key);
	public void removeExpiredSince(Date since);
}
