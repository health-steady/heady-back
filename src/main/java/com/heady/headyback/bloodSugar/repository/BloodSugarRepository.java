package com.heady.headyback.bloodSugar.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.dto.BloodSugarSummaryDto;
import com.heady.headyback.meal.domain.enumerated.MealType;

@Repository
public interface BloodSugarRepository extends JpaRepository<BloodSugar, Long> {
	@Query("""
			    SELECT bs FROM BloodSugar bs
			    LEFT JOIN FETCH bs.meal m
			    LEFT JOIN FETCH m.mealItems mi
			    LEFT JOIN FETCH mi.food
			    WHERE bs.member.id = :memberId
			      AND bs.measuredAt BETWEEN :start AND :end
			    ORDER BY bs.measuredAt
			""")
	List<BloodSugar> findAllByMemberIdAndMeasuredAtBetween(
			@Param("memberId") Long memberId,
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end
	);

	@Query("""
			    SELECT bs FROM BloodSugar bs
			    WHERE bs.member.id = :memberId
			      AND bs.mealType = :mealType
			      AND bs.measuredAt BETWEEN :start AND :end
			""")
	List<BloodSugar> findByMemberIdAndMealTypeAndMeasuredAtBetween(
			@Param("memberId") Long memberId,
			@Param("mealType") MealType mealType,
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end
	);

	@Query(value = """
			    SELECT
			        MAX(CASE
			                WHEN bs.measure_type = 'AFTER_MEAL' AND bs.meal_type = 'BREAKFAST'
			                THEN bs.level
			            END) AS breakfast,
			        MAX(CASE
			                WHEN bs.measure_type = 'AFTER_MEAL' AND bs.meal_type = 'LUNCH'
			                THEN bs.level
			            END) AS lunch,
			        MAX(CASE
			                WHEN bs.measure_type = 'AFTER_MEAL' AND bs.meal_type = 'DINNER'
			                THEN bs.level
			            END) AS dinner,
			        MAX(CASE
			                WHEN bs.measure_type = 'FASTING'
			                THEN bs.level
			            END) AS highestFasting,
			        MAX(CASE
			                WHEN bs.measure_type = 'AFTER_MEAL'
			                THEN bs.level
			            END) AS highestPostprandial
			    FROM blood_sugar bs
			    WHERE bs.member_id = :memberId
			      AND bs.measured_at BETWEEN :start AND :end
			""", nativeQuery = true)
	BloodSugarSummaryDto findSummaryByMemberIdAndMeasuredAtBetween(
			@Param("memberId") Long memberId,
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end
	);

	@Query("SELECT bs FROM BloodSugar bs WHERE bs.member.id = :memberId AND bs.measuredAt BETWEEN :start AND :end ORDER BY bs.measuredAt")
	List<BloodSugar> findByMemberIdAndMeasuredAtBetween(
			@Param("memberId") Long memberId,
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end
	);

}
