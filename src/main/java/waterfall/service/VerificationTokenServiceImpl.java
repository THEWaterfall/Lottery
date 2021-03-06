package waterfall.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import waterfall.dao.VerificationTokenDAO;
import waterfall.model.User;
import waterfall.model.VerificationToken;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Autowired
	private VerificationTokenDAO tokenDAO;
	
	@Override
	public VerificationToken createVerificationToken(User user, String token) {
		VerificationToken verificationToken = new VerificationToken(user, token);
		tokenDAO.save(verificationToken);
		
		return verificationToken;
	}

	@Override
	public VerificationToken findByToken(String token) {
		return tokenDAO.findByToken(token);
	}

	@Override
	public VerificationToken findByUser(User user) {
		return tokenDAO.findByUser(user);
	}
	
	@Override
	public void remove(VerificationToken token) {
		tokenDAO.remove(token);
	}
	
	@Override
	public User findTokenOwner(String token) {
		VerificationToken verificationToken = findByToken(token);
		return verificationToken.getUser();
	}

	@Override
	public void removeExpiredSince(Date since) {
		tokenDAO.removeExpiredSince(since);
	}

	@Override
	public void save(VerificationToken token) {
		tokenDAO.save(token);
	}

	@Override
	public void update(VerificationToken token) {
		tokenDAO.update(token);		
	}

	@Override
	public List<VerificationToken> findAll() {
		return tokenDAO.findAll();
	}

}
