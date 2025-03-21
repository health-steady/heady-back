package com.heady.headyback.auth.exception;

import org.springframework.http.HttpStatus;

import com.heady.headyback.common.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthExceptionCode implements ExceptionCode {
	;
	private final HttpStatus httpStatus;
	private final String message;
}
