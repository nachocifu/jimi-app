package edu.itba.paw.jimi.models;

public class User {
	
	private long id;
	private String username;
	private String password;
	
	public User(String name, long id, String password) {
		this.id = id;
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
