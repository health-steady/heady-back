package com.heady.headyback.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final ExceptionCode exceptionCode;
	private final String message;

	public CustomException(ExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
		this.message = exceptionCode.getMessage();
	}

}
