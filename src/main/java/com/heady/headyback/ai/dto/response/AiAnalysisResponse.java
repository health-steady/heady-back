package com.heady.headyback.ai.dto.response;

import java.util.List;

import com.heady.headyback.ai.dto.AiAnalysisDto;
import com.heady.headyback.bloodSugar.dto.response.BloodSugarWithMealResponse;
import com.heady.headyback.member.dto.response.MemberResponse;

public record AiAnalysisResponse(
		MemberResponse memberResponse,
		List<BloodSugarWithMealResponse> bloodSugarWithMealResponse,
		String bloodSugarAnalysis,
		String dietAnalysis,
		List<String> recommendedActionPlan
) {
	public static AiAnalysisResponse from(
			AiAnalysisDto aiAnalysisDto
	) {
		return new AiAnalysisResponse(
				MemberResponse.of(aiAnalysisDto.memberDto()),
				aiAnalysisDto.bloodSugarWithMealDtos().stream()
						.map(BloodSugarWithMealResponse::from)
						.toList(),
				aiAnalysisDto.bloodSugarAnalysis(),
				aiAnalysisDto.dietAnalysis(),
				aiAnalysisDto.recommendedActionPlan()
		);
	}
}
