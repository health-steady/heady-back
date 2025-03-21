package com.heady.headyback.auth.exception;

import org.springframework.http.HttpStatus;

import com.heady.headyback.common.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthExceptionCode implements ExceptionCode {
	AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
	AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "토큰 정보가 올바르지 않습니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
