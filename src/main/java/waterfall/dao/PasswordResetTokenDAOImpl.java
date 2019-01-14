package waterfall.dao;

import java.util.Date;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import waterfall.model.PasswordResetToken;
import waterfall.model.User;

@Repository
public class PasswordResetTokenDAOImpl extends AbstractDAO<Integer, PasswordResetToken> implements PasswordResetTokenDAO {

	public PasswordResetTokenDAOImpl() {
		setEntityClass(PasswordResetToken.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PasswordResetToken findByToken(String token) {
		Query<PasswordResetToken> query = getSession().createQuery("FROM PasswordResetToken WHERE token = :token")
																	.setParameter("token", token);
		
		PasswordResetToken passwordResetToken = query.uniqueResult();
		return passwordResetToken;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PasswordResetToken findByUser(User user) {
		Query<PasswordResetToken> query = getSession().createQuery("FROM PasswordResetToken WHERE User_id = :User_id")
				.setParameter("User_id", user.getId());

		PasswordResetToken passwordResetToken = query.uniqueResult();
		return passwordResetToken;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeExpiredSince(Date since) {
		Query<PasswordResetToken> query = getSession().createQuery("DELETE FROM PasswordResetToken WHERE expiryDate < :date")
				.setParameter("date", since);
		query.executeUpdate();
		
	}

}
