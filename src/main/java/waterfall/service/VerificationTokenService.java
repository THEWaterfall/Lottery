package waterfall.service;

import java.util.Date;

import waterfall.model.User;
import waterfall.model.VerificationToken;

public interface VerificationTokenService {
	public void createVerificationToken(User user, String token);
	public VerificationToken findByToken(String token);
	public VerificationToken findByUser(User user);
	public void remove(VerificationToken token);
	public User findTokenOwner(String token);
	public void removeExpiredSince(Date since);
}
