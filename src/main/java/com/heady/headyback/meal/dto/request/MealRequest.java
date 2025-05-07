package com.heady.headyback.meal.dto.request;

import static com.heady.headyback.meal.constant.MealValidationMessages.*;
import static com.heady.headyback.meal.constant.MealValidationPattern.*;
import static com.heady.headyback.meal.constant.MealValidationRegex.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MealRequest(

		@NotBlank(message = REQUIRED_MEAL_TYPE)
		@Pattern(regexp = MEAL_TYPE_REGEX, message = INVALID_MEAL_TYPE)
		String mealType,

		@NotNull(message = REQUIRED_MEAL_DATE_TIME)
		@JsonFormat(pattern = MEAL_DATE_TIME_PATTERN)
		@DateTimeFormat(pattern = MEAL_DATE_TIME_PATTERN)
		LocalDateTime mealDateTime,

		@NotEmpty(message = REQUIRED_FOOD_LIST)
		List<FoodInfo> foods,

		String memo
) {
	public record FoodInfo(

			String code,

			@NotBlank(message = REQUIRED_FOOD_NAME)
			String name
	) {
	}
}
