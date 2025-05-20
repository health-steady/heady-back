package com.heady.headyback.ai.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AiAnalysisResponse(
		@JsonProperty("bloodSugarAnalysis") String bloodSugarAnalysis,
		@JsonProperty("dietAnalysis") String dietAnalysis,
		@JsonProperty("recommendedActionPlan") List<String> recommendedActionPlan
) {
	@JsonCreator
	public AiAnalysisResponse {
	}
}
