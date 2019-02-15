package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.User;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class UserListDTO {

	private List<UserDTO> users;
	private PaginationDTO links;

	public UserListDTO() {
	}

	public UserListDTO(List<User> allUsers, URI baseUri, PaginationDTO links) {
		this.users = new LinkedList<>();

		for (User user : allUsers)
			this.users.add(new UserDTO(user, baseUri));

		this.links = links;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public PaginationDTO getLinks() {
		return links;
	}

	public void setLinks(PaginationDTO links) {
		this.links = links;
	}
}
