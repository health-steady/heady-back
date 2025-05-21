package com.heady.headyback.meal.exception;

import org.springframework.http.HttpStatus;

import com.heady.headyback.common.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MealExceptionCode implements ExceptionCode {
	MEAL_ALREADY_RECORDED(HttpStatus.BAD_REQUEST, "이미 기록된 식사입니다."),
	MEAL_NOT_FOUND(HttpStatus.NOT_FOUND, "식사를 찾을 수 없습니다."),
	MEAL_NO_AUTHORIZED(HttpStatus.UNAUTHORIZED, "식사 기록에 권한이 없습니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
