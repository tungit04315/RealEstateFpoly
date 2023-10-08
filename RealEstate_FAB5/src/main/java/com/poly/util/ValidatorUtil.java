package com.poly.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidatorUtil {

	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";

    public boolean isEmailValid(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return Pattern.matches(PHONE_REGEX, phoneNumber);
    }
}
