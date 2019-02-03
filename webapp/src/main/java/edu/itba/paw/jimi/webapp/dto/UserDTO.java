package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.User;

import java.net.URI;

public class UserDTO {
	
	private long id;
	private String username;
	private URI uri;
	
	public UserDTO() {
	}
	
	public UserDTO(User user, URI baseUri) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.uri = baseUri.resolve(String.valueOf(this.id));
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public URI getUri() {
		return uri;
	}
	
	public void setUri(URI uri) {
		this.uri = uri;
	}
}
