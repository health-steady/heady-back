package com.heady.headyback.user.exception;

import org.springframework.http.HttpStatus;

import com.heady.headyback.common.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberExceptionCode implements ExceptionCode {
	EMAIL_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
	EMAIL_ALREADY_USED(HttpStatus.CONFLICT, "이미 해당 이메일을 사용하는 유저가 존재합니다."),
	MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "존재하지 않는 사용자입니다."),
	PASSWORD_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호 형식입니다."),
	PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
