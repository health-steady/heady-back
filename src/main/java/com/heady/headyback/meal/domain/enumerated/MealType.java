package com.heady.headyback.meal.domain.enumerated;

import java.util.Arrays;

public enum MealType {
	BREAKFAST, LUNCH, DINNER, SNACK, NONE;

	public static MealType getMappedMealType(
			final String mealType
	) {
		return Arrays.stream(values())
				.filter(value -> value.name().toUpperCase().equals(mealType))
				.findAny()
				.orElseThrow(() -> new RuntimeException("없는 타입"));
		//TODO : 예외처리
	}
}
