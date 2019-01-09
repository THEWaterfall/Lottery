package waterfall.dao;

import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import waterfall.model.PersistentLoginToken;

@Repository
@Transactional
public class HibernatePersistentTokenRepositoryImpl extends AbstractDAO<String, PersistentLoginToken> implements PersistentTokenRepository {

	@SuppressWarnings("unchecked")
	public PersistentLoginToken findByUsername(String username) {
		Query<PersistentLoginToken> query = getSession().createQuery("FROM PersistentLoginToken WHERE username=:username").setParameter("username", username);
		PersistentLoginToken persistentLoginToken = query.uniqueResult();
		
		return persistentLoginToken;
	}
	
	@Override
	public void createNewToken(PersistentRememberMeToken tokenValue) {
		PersistentLoginToken token = new PersistentLoginToken();
		token.setToken(tokenValue.getTokenValue());
		token.setUsername(tokenValue.getUsername());
		token.setSeries(tokenValue.getSeries());
		token.setLastUsed(tokenValue.getDate());
		save(token);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		PersistentLoginToken token = findById(seriesId);
		return new PersistentRememberMeToken(token.getUsername(), token.getSeries(),
				token.getToken(), token.getLastUsed());
	}

	@Override
	public void removeUserTokens(String username) {
		PersistentLoginToken token = findByUsername(username);
		
		if(token != null)
			remove(token);
	
	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		PersistentLoginToken token = findById(seriesId);
		token.setToken(tokenValue);
		token.setLastUsed(lastUsed);
	    update(token);
	}

}
