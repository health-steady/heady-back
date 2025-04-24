package com.heady.headyback.meal.service;

import java.net.URI;

import org.springframework.stereotype.Service;

import com.heady.headyback.common.util.WebClientUtil;
import com.heady.headyback.meal.dto.response.FoodApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodService {

	private final WebClientUtil webClientUtil;

	public FoodApiResponse searchFoods(
			String foodName,
			String pageNo,
			String numOfRows
	) {
		URI uri = webClientUtil.buildRequestUri(foodName, pageNo, numOfRows);
		log.info("▶ OpenAPI URL = {}", uri);
		return webClientUtil.get(uri, FoodApiResponse.class);
	}

}
