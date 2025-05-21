package com.heady.headyback.bloodSugar.exception;

import org.springframework.http.HttpStatus;

import com.heady.headyback.common.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BloodSugarExceptionCode implements ExceptionCode {
	BLOOD_SUGAR_NOT_FOUND(HttpStatus.NOT_FOUND, "혈당 기록을 찾을 수 없습니다"),
	BLOOD_SUGAR_NO_AUTHORIZED(HttpStatus.UNAUTHORIZED, "혈당 기록에 대한 권한이 없습니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
