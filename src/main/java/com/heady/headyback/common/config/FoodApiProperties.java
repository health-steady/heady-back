package com.heady.headyback.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class FoodApiProperties {

	@Value("${food.api.base-url}")
	private String baseUrl;

	@Value("${food.api.service-key}")
	private String serviceKey;
}
