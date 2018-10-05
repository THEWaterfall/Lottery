package waterfall.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="type")
	private String type;
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", type=" + type + "]";
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
		if(!(o instanceof Role))
			return false;
		
		Role role = (Role) o;
		
		if(role.id == this.id && role.type.equals(this.type))
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
