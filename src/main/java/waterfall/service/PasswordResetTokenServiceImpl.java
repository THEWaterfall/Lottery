package waterfall.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import waterfall.dao.PasswordResetTokenDAO;
import waterfall.model.PasswordResetToken;
import waterfall.model.User;

@Service
@Transactional
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
	
	@Autowired
	private PasswordResetTokenDAO tokenDAO;

	@Override
	public void save(PasswordResetToken passwordResetToken) {
		tokenDAO.save(passwordResetToken);
	}

	@Override
	public void update(PasswordResetToken passwordResetToken) {
		tokenDAO.update(passwordResetToken);
	}

	@Override
	public void remove(PasswordResetToken passwordResetToken) {
		tokenDAO.remove(passwordResetToken);
	}

	@Override
	public List<PasswordResetToken> findAll() {
		return tokenDAO.findAll();
	}

	@Override
	public PasswordResetToken findByToken(String token) {
		return tokenDAO.findByToken(token);
	}

	@Override
	public PasswordResetToken findByUser(User user) {
		return tokenDAO.findByUser(user);
	}

	@Override
	public void removeExpiredSince(Date since) {
		tokenDAO.removeExpiredSince(since);
	}

	@Override
	public PasswordResetToken createPasswordResetToken(User user, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
		tokenDAO.save(passwordResetToken);
		
		return passwordResetToken;
	}

	@Override
	public User findTokenOwner(String token) {
		PasswordResetToken passwordResetToken = findByToken(token);
		
		return passwordResetToken.getUser()	;
	}
	
	@Override
	public boolean isResetPasswordTokenValid(Integer id, String token) {
		PasswordResetToken resetToken = tokenDAO.findByToken(token);
		
		if((resetToken == null) || (resetToken.getUser().getId() != id))
			return false;
		
		Calendar calendar = Calendar.getInstance();
		if(resetToken.getExpiryDate().getTime() - calendar.getTimeInMillis() <= 0)
			return false;
		
		return true;
	}
}
