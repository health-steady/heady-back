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

	public static Nutrient zero() {
		return new Nutrient(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	}

	public Nutrient add(Nutrient other) {
		return new Nutrient(
				this.carbohydrate.add(other.carbohydrate),
				this.protein.add(other.protein),
				this.fat.add(other.fat)
		);
	}
}
