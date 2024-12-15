package com.saarthi.commons;

public class AppConstants {
	public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$";
	public static final String MOBILE_REGEX = "\\d{10}";
	public static final String EXTRA_DIGIT_MOBILE_REGEX = "\\d{11,13}";
	
	// Exceptions
	public static final String EXTRA_DIGIT = "Enter a valid 10 digit mobile number";
	public static final String INVALID_USERID = "Invalid user id";
}
