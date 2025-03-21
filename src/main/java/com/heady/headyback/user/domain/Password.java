package com.heady.headyback.user.domain;

import static com.heady.headyback.user.exception.MemberExceptionCode.*;

import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.heady.headyback.common.exception.CustomException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Password {

	private static final Pattern PASSWORD_PATTERN =
			Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~`!@#$%^&*()_\\-+=]{6,}$");
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Column(name = "password")
	private String value;

	public static Password ofCreate(String value) {
		validatePasswordPattern(value);
		Password password = new Password();
		password.value = encrypt(value);;
		return password;
	}

	private static void validatePasswordPattern(String value) {
		if (value == null || !PASSWORD_PATTERN.matcher(value).matches()) {
			throw new CustomException(PASSWORD_INVALID_FORMAT);
		}
	}

	private static String encrypt(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

}
