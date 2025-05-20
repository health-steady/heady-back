package com.heady.headyback.ai.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GeminiResponse(List<Candidate> candidates) {
	@JsonCreator
	public GeminiResponse(@JsonProperty("candidates") List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public record Candidate(Content content) {
		@JsonCreator
		public Candidate(@JsonProperty("content") Content content) {
			this.content = content;
		}
	}

	public record Content(List<PartResponse> parts) {
		@JsonCreator
		public Content(@JsonProperty("parts") List<PartResponse> parts) {
			this.parts = parts;
		}
	}

	public record PartResponse(String text) {
		@JsonCreator
		public PartResponse(@JsonProperty("text") String text) {
			this.text = text;
		}
	}
}
