package com.popiang.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.popiang.model.SiteUser;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, SiteUser> {

	@Override
	public boolean isValid(SiteUser siteUser, ConstraintValidatorContext c) {

		String plainPassword = siteUser.getPlainPassword();
		String repeatPassword = siteUser.getRepeatPassword();
		
		if(plainPassword == null || plainPassword.length() == 0) {
			return true;
		}
		
		if(!plainPassword.equals(repeatPassword)) {
			return false;
		}
		
		return true;
	}

}
