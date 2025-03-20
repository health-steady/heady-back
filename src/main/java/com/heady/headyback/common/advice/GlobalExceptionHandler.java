package com.heady.headyback.common.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.common.exception.ExceptionCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationException(
			MethodArgumentNotValidException exception
	) {

		return ResponseEntity.badRequest()
				.body(exception
						.getBindingResult()
						.getAllErrors()
						.get(0)
						.getDefaultMessage()
				);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> handleCustomException(
			CustomException exception
	) {

		ExceptionCode exceptionCode = exception.getExceptionCode();
		return ResponseEntity
				.status(exceptionCode.getHttpStatus())
				.body(exception.getMessage());
	}
}
