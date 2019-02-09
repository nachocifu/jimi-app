package edu.itba.paw.jimi.webapp.dto.form.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserForm {

	@Size(min = 6, max = 40)
	@Pattern(regexp = "[a-zA-Z0-9]+", message = "{Pattern.message}")
	@NotBlank
	private String username;

	@Size(min = 6, max = 40)
	@NotBlank
	private String password;

	@Size(min = 6, max = 40)
	@NotBlank
	private String repeatPassword;

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

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
}
