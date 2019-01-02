package waterfall.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import waterfall.model.User;
import waterfall.model.VerificationToken;

@Repository
public class VerificationTokenDAOImpl extends AbstractDAO<VerificationToken> implements VerificationTokenDAO {

	public VerificationTokenDAOImpl() {
		setEntityClass(VerificationToken.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public VerificationToken findByToken(String token) {
		Query<VerificationToken> query = getSession().createQuery("FROM VerificationToken WHERE token=:token")
														.setParameter("token", token);
		VerificationToken verificationToken = query.uniqueResult();
		return verificationToken;
	}

	@SuppressWarnings("unchecked")
	@Override
	public VerificationToken findByUser(User user) {
		Query<VerificationToken> query = getSession().createQuery("FROM VerificationToken WHERE user_id=:user_id")
											.setParameter("user_id", user.getId());
		
		VerificationToken verificationToken = query.uniqueResult();
		return verificationToken;
	}
}
