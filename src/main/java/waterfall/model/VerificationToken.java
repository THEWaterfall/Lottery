package waterfall.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="verification_token")
public class VerificationToken {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="token")
	private String token;
	
	@Column(name="expiryDate")
	private Date expiryDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	public static final int EXPIRATION = 60 * 24;

	public VerificationToken() {
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	public VerificationToken(User user, String token) {
		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
		//return calendar.getTime() or return new Date(calendar.getTimeInMillis())
		return new Date(calendar.getTime().getTime());
	}
	
	public void refresh(String token) {
		this.token = token;
		calculateExpiryDate(EXPIRATION);
	}
	
	@Override
	public int hashCode() {
		final int constant = 11;
		int result = 1;
		result = result * constant + expiryDate.hashCode();
		result = result * constant + token.hashCode();
		result = result * constant + user.hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) 
			return true;
		if (o == null)
			return false;
		if(!(o instanceof VerificationToken))
			return false;
		
		VerificationToken verificationToken = (VerificationToken) o;
		
		if(verificationToken.expiryDate.equals(this.expiryDate) &&
		   verificationToken.token.equals(this.token) &&
		   verificationToken.user.equals(this.user) &&
		   verificationToken.id.equals(this.id)) {
			return true;
		} else {
			return false;
		}
	}
	
}
