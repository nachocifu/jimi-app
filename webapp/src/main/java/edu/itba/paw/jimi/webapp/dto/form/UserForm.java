package edu.itba.paw.jimi.webapp.dto.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserForm {
	
	@Size(min = 6, max = 40)
	@Pattern(regexp = "[a-zA-Z0-9]+", message = "{Pattern.message}")
	@NotBlank
	private String username;
	
	@Size(min = 6, max = 40)
	@NotBlank
	private String password;
	
	public UserForm() {
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
	
}
