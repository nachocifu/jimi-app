package edu.itba.paw.jimi.models;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
	@SequenceGenerator(sequenceName = "users_userid_seq", name = "users_userid_seq", allocationSize = 1)
	@Column(name = "userid")
	private Long id;

	@Column(length = 100, nullable = false)
	private String username;

	@Column(length = 100, nullable = false)
	private String password;

	public final static String ROLE_ADMIN = "ROLE_ADMIN";
	public final static String ROLE_USER = "ROLE_USER";
	public final static String ADMIN = "ADMIN";
	public final static String USER = "USER";

	@ElementCollection(targetClass=String.class)
	@JoinTable(name="user_roles",
			joinColumns = {@JoinColumn(name="userid")}
			)
	private Set<String> roles;

	/* package */ User() {
		// Just for Hibernate, we love you!
	}
	
	public User(String name, long id, String password) {
		this.id = id;
		this.username = name;
		this.password = password;
	}

	public User(String name, String password) {
		this.username = name;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		if(roles != null)
			this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		return username.equals(user.username);
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + username.hashCode();
		return result;
	}
}
