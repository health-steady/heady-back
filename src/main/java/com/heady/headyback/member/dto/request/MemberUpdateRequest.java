package com.heady.headyback.member.dto.request;

import java.math.BigDecimal;

public record MemberUpdateRequest(
		String name,
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
