package com.heady.headyback.meal.domain;

import java.math.BigDecimal;

public record Nutrient(
		BigDecimal carbohydrate,
		BigDecimal protein,
		BigDecimal fat
) {
	public static Nutrient of(
			BigDecimal carbohydrate,
			BigDecimal protein,
			BigDecimal fat
	) {
		return new Nutrient(carbohydrate, protein, fat);
	}
}
