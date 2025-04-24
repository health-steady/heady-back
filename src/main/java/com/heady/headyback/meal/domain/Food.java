package com.heady.headyback.meal.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.heady.headyback.meal.domain.enumerated.Unit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Meal meal;

	/** API 원본 식품코드 */
	@Column(nullable = false, length = 50)
	private String code;

	@Column(nullable = false)
	private String name;

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

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Unit unit;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	static Food ofAdd(
			Meal meal,
			String name
	) {
		Food food = new Food();
		food.meal = meal;
		food.name = name;
		return food;
	}
}
