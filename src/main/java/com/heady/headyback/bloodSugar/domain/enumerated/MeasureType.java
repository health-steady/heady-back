package com.heady.headyback.bloodSugar.domain.enumerated;

import java.util.Arrays;

public enum MeasureType {
	BEFORE_MEAL(true),
	AFTER_MEAL(true),
	BEDTIME(false),
	FASTING(false),
	RANDOM(false);

	private final boolean hasMeal;

	MeasureType(boolean hasMeal) {
		this.hasMeal = hasMeal;
	}

	public static MeasureType getMappedMeasureType(
			final String measurementTimeType
	) {
		return Arrays.stream(values())
				.filter(value -> value.name().toUpperCase().equals(measurementTimeType))
				.findAny()
				.orElseThrow(() -> new RuntimeException("없는 타입"));
		//TODO : 예외처리
	}

	public boolean requiresMeal() {
		return hasMeal;
	}
}
