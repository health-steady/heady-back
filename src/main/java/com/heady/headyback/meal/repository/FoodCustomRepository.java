package com.heady.headyback.meal.repository;

import java.util.List;

import com.heady.headyback.meal.domain.Food;

public interface FoodCustomRepository {
	List<Food> searchByKeyword(String keyword, long offset, long limit);
	long countByKeyword(String keyword);
}
