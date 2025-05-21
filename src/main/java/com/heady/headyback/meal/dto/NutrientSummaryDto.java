package com.heady.headyback.meal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.heady.headyback.meal.domain.Nutrient;

public record NutrientSummaryDto(
		LocalDate date,
		BigDecimal carbohydrate,
		BigDecimal protein,
		BigDecimal fat
) {
	public static NutrientSummaryDto of(LocalDate date, Nutrient nutrient) {
		return new NutrientSummaryDto(
				date,
				nutrient.carbohydrate(),
				nutrient.protein(),
				nutrient.fat()
		);
	}
}
