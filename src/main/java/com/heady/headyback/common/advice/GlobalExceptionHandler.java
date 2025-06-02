package com.heady.headyback.common.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.common.exception.ExceptionCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationException(
			MethodArgumentNotValidException exception
	) {

		log.info("MethodArgumentNotValidException : {}",exception.getMessage());
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
		StackTraceElement[] stackTrace = exception.getStackTrace();
		StackTraceElement origin = stackTrace[0];
		ExceptionCode exceptionCode = exception.getExceptionCode();
		log.error("CustomException thrown at {}.{}({}:{}) - Message: {}",
				origin.getClassName(),
				origin.getMethodName(),
				origin.getFileName(),
				origin.getLineNumber(),
				exception.getMessage()
		);
		return ResponseEntity
				.status(exceptionCode.getHttpStatus())
				.body(exception.getMessage());
	}
}
