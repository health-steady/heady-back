package com.heady.headyback.member.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.heady.headyback.member.domain.enumerated.Gender;

public record MemberUpdateRequest(
		String name,
		Gender gender,
		LocalDate birthDate,
		BigDecimal height,
		BigDecimal weight,
		Integer fastingBloodSugar,
		Integer postprandialBloodSugar,
		BigDecimal carbohydrate,
		BigDecimal protein,
		BigDecimal fat,
		Integer calories
) {
}
