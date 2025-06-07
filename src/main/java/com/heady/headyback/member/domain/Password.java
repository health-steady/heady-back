package com.heady.headyback.member.domain;

import static com.heady.headyback.member.constant.Argon2ConfigConstants.*;
import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.util.regex.Pattern;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.heady.headyback.common.exception.CustomException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

	private static final Pattern PASSWORD_PATTERN =
			Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~`!@#$%^&*()_\\-+=]{6,}$");

	// private static final PasswordEncoder passwordEncoder =
	// 		new Argon2PasswordEncoder(
	// 				SALT_LENGTH,
	// 				HASH_LENGTH,
	// 				PARALLELISM,
	// 				MEMORY,
	// 				ITERATIONS
	// 		);

	private static final PasswordEncoder passwordEncoder =
			new BCryptPasswordEncoder(8);

	@Column(name = "password")
	private String value;

	public static Password ofCreate(String value) {
		validatePasswordPattern(value);
		Password password = new Password();
		password.value = encrypt(value);
		return password;
	}

	public static Password of(String value) {
		Password password = new Password();
		password.value = value;
		return password;
	}

	public void matches(String rawPassword) {
		if (!passwordEncoder.matches(rawPassword, this.value)) {
			throw new CustomException(PASSWORD_INVALID);
		}
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
