package com.heady.headyback.bloodSugar.dto.response;

import java.time.LocalDateTime;

import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.bloodSugar.dto.BloodSugarDto;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.response.MealResponse;

public record BloodSugarResponse(
		Long id,
		Integer level,
		LocalDateTime measuredAt,
		MeasureType measureType,
		String memo,
		MealType mealType,
		MealResponse meal
) {
	public static BloodSugarResponse of(BloodSugarDto bloodSugarDto) {
		return new BloodSugarResponse(
				bloodSugarDto.id(),
				bloodSugarDto.level(),
				bloodSugarDto.measuredAt(),
				bloodSugarDto.measureType(),
				bloodSugarDto.memo(),
				bloodSugarDto.mealType(),
				MealResponse.ofNullable(bloodSugarDto.mealDto())
		);
	}
}
