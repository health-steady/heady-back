package com.heady.headyback.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heady.headyback.meal.domain.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, String>, FoodCustomRepository {
}
