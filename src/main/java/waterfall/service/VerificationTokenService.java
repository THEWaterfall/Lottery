package waterfall.service;

import java.util.Date;
import java.util.List;

import waterfall.model.User;
import waterfall.model.VerificationToken;

public interface VerificationTokenService {
	public void save(VerificationToken token);
	public void update(VerificationToken token);
	public void remove(VerificationToken token);
	public List<VerificationToken> findAll();
	public VerificationToken createVerificationToken(User user, String token);
	public VerificationToken findByToken(String token);
	public VerificationToken findByUser(User user);
	public User findTokenOwner(String token);
	public void removeExpiredSince(Date since);
}
