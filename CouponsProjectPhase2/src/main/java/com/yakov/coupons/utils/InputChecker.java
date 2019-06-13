package com.yakov.coupons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yakov.coupons.enums.CategoriesEnum;
import com.yakov.coupons.exceptions.MyException;

public class InputChecker {
	// Validate for a real emaul address
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$",
			Pattern.CASE_INSENSITIVE);

	// name regex (allowing Alphabets, Dots, Spaces)
	public static final Pattern VALID_NAME_REGEX = Pattern.compile("[A-Z][a-z]*", Pattern.CASE_INSENSITIVE);
	// url regex:
	public static final Pattern VALID_URL = Pattern.compile(
			"(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)", Pattern.CASE_INSENSITIVE);
	// 6-20 Characters at least : one upper case one lower case one digit
	public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})",
			Pattern.CASE_INSENSITIVE);

	public static boolean isValidEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.matches();
	}

	public static boolean isValidPassword(String password) throws MyException {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.matches();
	}

	public static boolean isValidName(String name) throws MyException {
		Matcher matcher = VALID_NAME_REGEX.matcher(name);
		return matcher.matches();
	}
	public static boolean isValidUrl(String url)throws MyException{
		Matcher matcher=VALID_URL.matcher(url);
		return matcher.matches();
		
	}

	public static boolean isValidId(long id) {
		if (id < 0) {
			return false;
		}
		return true;
	}

	public static boolean isValidPrice(double price) {
		if (price < 0) {
			return false;
		}
		return true;
	}

	public static boolean isValidCategory(CategoriesEnum category) throws MyException {
		if (category != null) {
			return true;
		}
		return false;
	}
}
