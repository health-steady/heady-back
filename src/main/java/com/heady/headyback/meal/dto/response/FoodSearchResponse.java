package com.heady.headyback.meal.dto.response;

import java.util.List;
import java.util.stream.Collectors;

public record FoodSearchResponse(
		String code,
		String name,
		String manufacturerName
) {
	public static List<FoodSearchResponse> from(FoodApiResponse foodApiResponse) {
		return foodApiResponse.response().body().items().stream()
				.map(foodItem -> new FoodSearchResponse(
						foodItem.code(),
						foodItem.name(),
						foodItem.manufacturerName()
				))
				.collect(Collectors.toList());
	}
}
