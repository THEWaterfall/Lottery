package waterfall.dao;

import java.util.List;

import waterfall.model.User;
import waterfall.model.VerificationToken;

public interface VerificationTokenDAO {
	public void save(VerificationToken verificationToken);
	public void update(VerificationToken verificationToken);
	public void remove(VerificationToken verificationToken);
	public List<VerificationToken> findAll();	
	public VerificationToken findByToken(String token);
	public VerificationToken findByUser(User user);
}
