package edu.itba.paw.jimi.webapp.validator;

import edu.itba.paw.jimi.form.UserForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
	
	@Override
	public boolean supports(Class clazz) {
		return UserForm.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		UserForm user = (UserForm) target;
		
		if (!user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("password", "non_matching_passwords");
			errors.rejectValue("repeatPassword", "non_matching_passwords");
		}
	}
}
