package com.heady.headyback.member.domain;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.util.regex.Pattern;

import com.heady.headyback.common.exception.CustomException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

	private static final Pattern EMAIL_PATTERN =
			Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

	@Column(name = "email", unique = true)
	private String value;

	public static Email ofCreate(String email) {
		validateEmailPattern(email);
		Email newEmail = new Email();
		newEmail.value = email;
		return newEmail;
	}

	private static void validateEmailPattern(String email) {
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new CustomException(EMAIL_INVALID_FORMAT);
		}
	}
}
