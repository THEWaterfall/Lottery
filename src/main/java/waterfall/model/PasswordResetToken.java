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
@Table(name="Password_Reset_Token")
public class PasswordResetToken {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="token")
	private String token;
	
	@Column(name="expiryDate")
	private Date expiryDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="User_id")
	private User user;
	
	private static final int EXPIRATION = 60 * 24;
	
	public PasswordResetToken() {
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	public PasswordResetToken(User user, String token) {
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
	
	public Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
		
		return calendar.getTime();
	}
	
	public void refresh(String token) {
		this.token = token;
		calculateExpiryDate(EXPIRATION);
	}
	
	@Override
	public int hashCode() {
		final int constant = 22;
		int result = 1;
		result = result * constant + expiryDate.hashCode();
		result = result * constant + token.hashCode();
		result = result * constant + user.hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		if(o == null)
			return false;
		if(!(o instanceof PasswordResetToken))
			return false;
		
		PasswordResetToken resetToken = (PasswordResetToken) o;
		
		if(resetToken.expiryDate.equals(this.expiryDate) &&
				resetToken.token.equals(this.token) &&
				resetToken.user.equals(this.user) &&
				resetToken.id.equals(this.id)) {
					return true;
				} else {
					return false;
				}
	}
}
