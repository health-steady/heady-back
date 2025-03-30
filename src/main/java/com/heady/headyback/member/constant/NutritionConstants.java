package com.heady.headyback.member.constant;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class NutritionConstants {

	// 기본값
	public static final int DEFAULT_FASTING_BLOOD_SUGAR = 99;
	public static final int DEFAULT_POSTPRANDIAL_BLOOD_SUGAR = 140;

	// 활동 계수
	public static final BigDecimal ACTIVITY_FACTOR = BigDecimal.valueOf(1.375);

	// BMR 계산 상수
	public static final BigDecimal BMR_WEIGHT_FACTOR = BigDecimal.valueOf(10.0);
	public static final BigDecimal BMR_HEIGHT_FACTOR = BigDecimal.valueOf(6.25);
	public static final BigDecimal BMR_AGE_FACTOR = BigDecimal.valueOf(5.0);
	public static final BigDecimal BMR_MALE_CONSTANT = BigDecimal.valueOf(5.0);
	public static final BigDecimal BMR_FEMALE_CONSTANT = BigDecimal.valueOf(-161.0);

	// 영양소 비율
	public static final BigDecimal CARBOHYDRATE_RATIO = BigDecimal.valueOf(0.55);
	public static final BigDecimal PROTEIN_RATIO = BigDecimal.valueOf(0.15);
	public static final BigDecimal FAT_RATIO = BigDecimal.valueOf(0.30);

	// 영양소 1g당 칼로리
	public static final int KCAL_PER_GRAM_CARBOHYDRATE = 4;
	public static final int KCAL_PER_GRAM_PROTEIN = 4;
	public static final int KCAL_PER_GRAM_FAT = 9;

	// 기타
	public static final int NUTRIENT_SCALE = 2;
	public static final int CALORIES_SCALE = 0;
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
}
