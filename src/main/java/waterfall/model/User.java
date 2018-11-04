package waterfall.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user")
public class User {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(name="username")
	private String username;
	
	@NotBlank
	@Column(name="password")
	private String password;
	
	@NotBlank
	@Column(name="email")
	private String email;
	
	@NotNull(message="must not be empty")
	@Min(value=0, message="must be >= 0")
	@Column(name="credits")
	private Integer credits;
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_role", joinColumns = {@JoinColumn(name="user_id")},
							     inverseJoinColumns = {@JoinColumn(name="role_id")})
	private Set<Role> roles;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="user_profile_id")
	private UserProfile profile;
	
	public User() {
		
	}

	public User(Integer id, @NotBlank String username, @NotBlank String password, @NotBlank String email,
			@NotNull(message = "must not be empty") @Min(value = 0, message = "must be >= 0") Integer credits,
			@NotEmpty Set<Role> roles, UserProfile profile) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.credits = credits;
		this.roles = roles;
		this.profile = profile;
	}
	
	public User(@NotBlank String username, @NotBlank String password, @NotBlank String email,
			@NotNull(message = "must not be empty") @Min(value = 0, message = "must be >= 0") Integer credits,
			@NotEmpty Set<Role> roles, UserProfile profile) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.credits = credits;
		this.roles = roles;
		this.profile = profile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
	
	@Override 
	public int hashCode() {
		final int constant = 11;
		int result = 1;
		result = result * constant + id.hashCode();
		result = result * constant + username.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(!(o instanceof User))
			return false;
		
		User user = (User) o;
		
		if(user.id == this.id && user.username.equals(this.username))
			return true;
		else 
			return false;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=encrypted, email=" + email
				+ ", credits=" + credits + ", roles=" + roles + ", profile=" + profile + "]";
	}
	
}
