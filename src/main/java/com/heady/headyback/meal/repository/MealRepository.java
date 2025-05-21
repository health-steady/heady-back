package com.heady.headyback.meal.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.enumerated.MealType;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

	@Query("""
			    SELECT m FROM Meal m
			    WHERE m.member.id = :memberId
			      AND DATE(m.mealDateTime) = DATE(:measuredAt)
			      AND m.mealType = :mealType
			""")
	Optional<Meal> findByMemberIdAndMealTypeAndMeasuredAt(
			@Param("memberId") Long memberId,
			@Param("mealType") MealType mealType,
			@Param("measuredAt") LocalDateTime measuredAt
	);

	@Query("""
			    SELECT m FROM Meal m
				LEFT JOIN FETCH m.mealItems mi
				LEFT JOIN FETCH mi.food
			    WHERE m.member.id = :memberId
			      AND m.mealDateTime BETWEEN :start AND :end
			""")
	List<Meal> findByMemberIdAndMealDateTimeBetween(
			@Param("memberId") Long memberId,
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end
	);

	boolean existsByMemberIdAndMealTypeAndMealDateTimeBetween(
			Long memberId,
			MealType mealType,
			LocalDateTime startOfDay,
			LocalDateTime endOfDay
	);
}
