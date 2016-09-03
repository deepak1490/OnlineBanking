package com.me.FinalProject.Validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.me.FinalProject.Model.User;

public class UserValidator implements org.springframework.validation.Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
				"validate.userName", "Your Name Is Incorrenct");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword",
				"validate.userPassword", "Your password Is Incorrenct");

	}

}
