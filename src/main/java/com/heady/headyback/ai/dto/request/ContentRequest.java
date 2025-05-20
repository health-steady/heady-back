package com.heady.headyback.ai.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ContentRequest(List<Content> contents) {
	@JsonCreator
	public ContentRequest(@JsonProperty("contents") List<Content> contents) {
		this.contents = contents;
	}

	public record Content(List<Part> parts) {
		@JsonCreator
		public Content(@JsonProperty("parts") List<Part> parts) {
			this.parts = parts;
		}
	}

	public record Part(String text) {
		@JsonCreator
		public Part(@JsonProperty("text") String text) {
			this.text = text;
		}
	}
}
