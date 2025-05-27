package com.heady.headyback.auth.exception;

import org.springframework.http.HttpStatus;

import com.heady.headyback.common.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthExceptionCode implements ExceptionCode {
	AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
	AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "토큰 정보가 올바르지 않습니다."),
	OAUTH_USERINFO_RESPONSE_EMPTY(HttpStatus.BAD_REQUEST, "소셜 로그인 중 사용자 정보 응답이 비어 있습니다."),
	OAUTH_TOKEN_REQUEST_FAILED(HttpStatus.BAD_REQUEST, "소셜 로그인 중 액세스 토큰 요청에 실패했습니다."),
	AUTH_NOT_SUPPORTED_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 소셜 로그인 플랫폼입니다");
	private final HttpStatus httpStatus;
	private final String message;
}
