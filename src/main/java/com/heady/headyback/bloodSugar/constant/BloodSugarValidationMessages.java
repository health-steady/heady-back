package com.heady.headyback.bloodSugar.constant;

public class BloodSugarValidationMessages {

	public static final String REQUIRED_MEASURED_AT = "측정 시간은 필수입니다.";
	public static final String REQUIRED_MEASUREMENT_TYPE = "측정 시간 유형은 필수입니다.";
	public static final String INVALID_MEASUREMENT_TYPE = "측정 시점은 BEFORE_MEAL 또는 AFTER_MEAL이어야 합니다.";

	public static final String REQUIRED_MEAL_TYPE = "식사 시점 유형은 필수입니다.";
	public static final String INVALID_MEAL_TYPE = "식사 시점은 BREAKFAST, LUNCH, DINNER 중 하나여야 합니다.";

	public static final String REQUIRED_LEVEL = "혈당 수치는 필수입니다.";
	public static final String MIN_LEVEL = "혈당 수치는 40 이상이어야 합니다.";
	public static final String MAX_LEVEL = "혈당 수치는 400 이하이어야 합니다.";
}
