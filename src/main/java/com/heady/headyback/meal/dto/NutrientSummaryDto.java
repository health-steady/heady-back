package com.heady.headyback.meal.dto;

import java.math.BigDecimal;

import com.heady.headyback.meal.domain.Nutrient;

public record NutrientSummaryDto(
		BigDecimal carbohydrate,
		BigDecimal protein,
		BigDecimal fat
) {
	public static NutrientSummaryDto of(Nutrient nutrient) {
		return new NutrientSummaryDto(
				nutrient.carbohydrate(),
				nutrient.protein(),
				nutrient.fat()
		);
	}
}
