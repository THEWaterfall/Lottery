package waterfall.service;

import java.util.Date;
import java.util.List;

import waterfall.model.PasswordResetToken;
import waterfall.model.User;

public interface PasswordResetTokenService {
	public void save(PasswordResetToken passwordResetToken);
	public void update(PasswordResetToken passwordResetToken);
	public void remove(PasswordResetToken passwordResetToken);
	public List<PasswordResetToken> findAll();	
	public PasswordResetToken findByToken(String token);
	public PasswordResetToken findByUser(User user);
	public void removeExpiredSince(Date since);
	public PasswordResetToken createPasswordResetToken(User user, String token);
	public User findTokenOwner(String token);
	public boolean isResetPasswordTokenValid(Integer id, String token);
}
