package com.saarthi.login;

import com.saarthi.commons.AppConstants;

import lombok.Data;

@Data
public class LoginRequest {
	private String userId;
	private String password;
	
	public String getLoginType(){
		// TBD Imp: How to throw exceptions directly in line 19,20 even when return type LoginType 
		if(userId.matches(AppConstants.EMAIL_REGEX))
			return LoginType.EMAIL.name();
		if (userId.matches(AppConstants.MOBILE_REGEX))
			return LoginType.MOBILE.name();
		if (userId.matches(AppConstants.EXTRA_DIGIT_MOBILE_REGEX))
			return AppConstants.EXTRA_DIGIT;
		return AppConstants.INVALID_USERID;
	}
}
