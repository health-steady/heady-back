package com.heady.headyback.meal.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FoodApiResponse(

		@JsonProperty("header")
		Header header,

		@JsonProperty("body")
		Body body
) {
	public record Header(
			@JsonProperty("resultCode") String resultCode,
			@JsonProperty("resultMsg")  String resultMsg
	) {}

	public record Body(
			@JsonProperty("items")        List<FoodItem> items,
			@JsonProperty("numOfRows")    Integer numOfRows,
			@JsonProperty("pageNo")       Integer pageNo,
			@JsonProperty("totalCount")   Integer totalCount
	) {}

	public record FoodItem(
			@JsonProperty("foodCd")
			String code,
			@JsonProperty("foodNm")
			String name,

			@JsonProperty("enerc")
			BigDecimal calories,
			@JsonProperty("chocdf")
			BigDecimal carbohydrates,
			@JsonProperty("sugar")
			BigDecimal sugar,
			@JsonProperty("fibtg")
			BigDecimal fiber,
			@JsonProperty("prot")
			BigDecimal protein,
			@JsonProperty("fatce")
			BigDecimal fat,
			@JsonProperty("fasat")
			BigDecimal satFat,
			@JsonProperty("fatrn")
			BigDecimal transFat,
			@JsonProperty("nat")
			BigDecimal sodium,

			@JsonProperty("mfrNm")
			String manufacturerName
	) {
	}
}
