package com.heady.headyback.bloodSugar.dto.request;

import static com.heady.headyback.bloodSugar.constant.BloodSugarValidationMessages.*;
import static com.heady.headyback.bloodSugar.constant.BloodSugarValidationPattern.*;
import static com.heady.headyback.bloodSugar.constant.BloodSugarValidationRegex.*;
import static com.heady.headyback.bloodSugar.constant.BloodSugarValidationValue.*;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RecordRequest(

		@NotNull(message = REQUIRED_MEASURED_AT)
		@JsonFormat(pattern = MEASURED_AT_PATTERN)
		@DateTimeFormat(pattern = MEASURED_AT_PATTERN)
		LocalDateTime measuredAt,

		@NotBlank(message = REQUIRED_MEASUREMENT_TYPE)
		@Pattern(regexp = MEASUREMENT_TYPE_REGEX, message = INVALID_MEASUREMENT_TYPE)
		String measureType,

		@NotBlank(message = REQUIRED_MEAL_TYPE)
		@Pattern(regexp = MEAL_TYPE_REGEX, message = INVALID_MEAL_TYPE)
		String mealType,

		@NotNull(message = REQUIRED_LEVEL)
		@Min(value = MIN_LEVEL_VALUE, message = MIN_LEVEL)
		@Max(value = MAX_LEVEL_VALUE, message = MAX_LEVEL)
		Integer level,

		String memo
) {
}
