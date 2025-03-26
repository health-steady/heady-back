package com.heady.headyback.meal.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public record RecordRequest(
		String mealType,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
		LocalDateTime mealDateTime,
		List<String> name,
		String memo
) {
}
