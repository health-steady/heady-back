package com.heady.headyback.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
