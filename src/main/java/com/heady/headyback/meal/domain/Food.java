package com.heady.headyback.meal.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "foods")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Food {

	@Id
	private String code;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String manufacturerName;

	@Column(precision = 9, scale = 2)
	private BigDecimal calories;
	@Column(precision = 9, scale = 2)
	private BigDecimal carbohydrates;
	@Column(precision = 9, scale = 2)
	private BigDecimal sugar;
	@Column(precision = 9, scale = 2)
	private BigDecimal fiber;
	@Column(precision = 9, scale = 2)
	private BigDecimal protein;
	@Column(precision = 9, scale = 2)
	private BigDecimal fat;
	@Column(precision = 9, scale = 2)
	private BigDecimal satFat;
	@Column(precision = 9, scale = 2)
	private BigDecimal transFat;
	@Column(precision = 9, scale = 2)
	private BigDecimal sodium;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	public static Food ofRecord(
			String code,
			String name,
			String manufacturerName,
			BigDecimal calories,
			BigDecimal carbohydrates,
			BigDecimal sugar,
			BigDecimal fiber,
			BigDecimal protein,
			BigDecimal fat,
			BigDecimal satFat,
			BigDecimal transFat,
			BigDecimal sodium
	) {
		Food food = new Food();
		food.code = code;
		food.name = name;
		food.manufacturerName = manufacturerName;
		food.calories = calories;
		food.carbohydrates = carbohydrates;
		food.sugar = sugar;
		food.fiber = fiber;
		food.protein = protein;
		food.fat = fat;
		food.satFat = satFat;
		food.transFat = transFat;
		food.sodium = sodium;
		return food;
	}
}
