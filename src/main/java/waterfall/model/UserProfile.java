package waterfall.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_profile")
public class UserProfile {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="birthday")
	private Date birthday;
	
	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", gender=" + gender
				+ ", birthday=" + birthday + "]";
	}

	@Override
	public int hashCode() {
		final int constant = 11;
		int result = 1;
		result = result * constant + id.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		if(o == null)
			return false;
		if(!(o instanceof UserProfile))
			return false;
		
		UserProfile userProfile = (UserProfile) o;
		
		if(userProfile.id == this.id)
			return true;
		else 
			return false;
	
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
