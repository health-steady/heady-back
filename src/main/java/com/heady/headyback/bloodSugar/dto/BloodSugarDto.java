package com.heady.headyback.bloodSugar.dto;

import java.time.LocalDateTime;

import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.meal.domain.enumerated.MealType;

public record BloodSugarDto(
		Long id,
		Integer level,
		LocalDateTime measuredAt,
		MeasureType measureType,
		String memo,
		MealType mealType
) {
	public static BloodSugarDto from(BloodSugar bloodSugar) {
		return new BloodSugarDto(
				bloodSugar.getId(),
				bloodSugar.getLevel(),
				bloodSugar.getMeasuredAt(),
				bloodSugar.getMeasureType(),
				bloodSugar.getMemo(),
				bloodSugar.getMealType()
		);
	}
}
