package com.heady.headyback.user.domain;

import static com.heady.headyback.user.exception.MemberExceptionCode.*;

import java.util.regex.Pattern;

import com.heady.headyback.common.exception.CustomException;

import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

	private static final Pattern EMAIL_PATTERN =
			Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

	private String email;

	public static Email ofCreate(String email) {
		validateEmailPattern(email);
		Email newEmail = new Email();
		newEmail.email = email;
		return newEmail;
	}

	private static void validateEmailPattern(String email) {
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new CustomException(EMAIL_INVALID_FORMAT);
		}
	}
}
