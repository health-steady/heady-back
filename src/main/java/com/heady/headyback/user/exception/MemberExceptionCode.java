package com.heady.headyback.user.exception;

import org.springframework.http.HttpStatus;

import com.heady.headyback.common.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberExceptionCode implements ExceptionCode {
	EMAIL_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
