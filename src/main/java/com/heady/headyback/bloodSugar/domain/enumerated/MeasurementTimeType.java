package com.heady.headyback.bloodSugar.domain.enumerated;

import java.util.Arrays;

public enum MeasurementTimeType {
	BEFORE_MEAL,
	AFTER_MEAL,
	BEDTIME,
	RANDOM;

	public static MeasurementTimeType getMappedMeasurementTimeType(
			final String measurementTimeType
	) {
		return Arrays.stream(values())
				.filter(value -> value.name().toUpperCase().equals(measurementTimeType))
				.findAny()
				.orElseThrow(() -> new RuntimeException("없는 타입"));
		//TODO : 예외처리
	}
}
