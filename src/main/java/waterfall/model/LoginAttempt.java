package waterfall.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="login_attempt")
public class LoginAttempt {
	
	@Column(name="key")
	private String key;
	
	@Column(name="attempts")
	private Integer attempts;
	
	@Column(name="expiryDate")
	private Date expiryDate;
	
	private static final int EXPIRATION = 20;
	
	public LoginAttempt() {
		expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	public LoginAttempt(String key, Integer attempts) {
		this.key = key;
		this.attempts = attempts;
		expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public Date calculateExpiryDate(final int expiryTimeInMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
		
		return calendar.getTime();
	}
}