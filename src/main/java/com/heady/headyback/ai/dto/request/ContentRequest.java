package com.heady.headyback.ai.dto.request;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Gemini generateContent 호출용 요청 DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContentRequest(
		List<Content> contents,
		GenerationConfig generationConfig
) {
	@JsonCreator
	public ContentRequest(
			@JsonProperty("contents") List<Content> contents,
			@JsonProperty("generationConfig") GenerationConfig generationConfig
	) {
		this.contents = contents;
		this.generationConfig = generationConfig;
	}

	public record Content(
			List<Part> parts
	) {
		@JsonCreator
		public Content(@JsonProperty("parts") List<Part> parts) {
			this.parts = parts;
		}
	}

	public record Part(
			String text
	) {
		@JsonCreator
		public Part(@JsonProperty("text") String text) {
			this.text = text;
		}
	}

	/**
	 * generationConfig 정의
	 */
	public record GenerationConfig(
			String responseMimeType,
			ResponseSchema responseSchema
	) {
		@JsonCreator
		public GenerationConfig(
				@JsonProperty("responseMimeType") String responseMimeType,
				@JsonProperty("responseSchema") ResponseSchema responseSchema
		) {
			this.responseMimeType = responseMimeType;
			this.responseSchema = responseSchema;
		}
	}

	/**
	 * responseSchema 정의: type에 따라 items(ARRAY)/properties(OBJECT) 사용
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record ResponseSchema(
			String type,
			SchemaObject items,
			Map<String, PropertySchema> properties,
			List<String> propertyOrdering
	) {
		@JsonCreator
		public ResponseSchema(
				@JsonProperty("type") String type,
				@JsonProperty("items") SchemaObject items,
				@JsonProperty("properties") Map<String, PropertySchema> properties,
				@JsonProperty("propertyOrdering") List<String> propertyOrdering
		) {
			this.type = type;
			this.items = items;
			this.properties = properties;
			this.propertyOrdering = propertyOrdering;
		}
	}

	/**
	 * ARRAY->OBJECT 내부 정의(OBJECT 스키마를 정의할 때 사용)
	 */
	public record SchemaObject(
			String type,
			Map<String, PropertySchema> properties,
			List<String> propertyOrdering
	) {
		@JsonCreator
		public SchemaObject(
				@JsonProperty("type") String type,
				@JsonProperty("properties") Map<String, PropertySchema> properties,
				@JsonProperty("propertyOrdering") List<String> propertyOrdering
		) {
			this.type = type;
			this.properties = properties;
			this.propertyOrdering = propertyOrdering;
		}
	}

	/**
	 * properties의 값으로 사용 가능한 스키마 정의
	 */
	public sealed interface PropertySchema {

		record TypeOnly(
				String type
		) implements PropertySchema {
			@JsonCreator
			public TypeOnly(@JsonProperty("type") String type) {
				this.type = type;
			}
		}

		record ArrayOfString(
				String type,
				TypeOnly items
		) implements PropertySchema {
			@JsonCreator
			public ArrayOfString(
					@JsonProperty("type") String type,
					@JsonProperty("items") TypeOnly items
			) {
				this.type = type;
				this.items = items;
			}
		}
	}
}
