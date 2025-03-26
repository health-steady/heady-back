package com.heady.headyback.meal.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.member.domain.Member;

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
import lombok.Getter;

@Table(name = "meals")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Member member;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private MealType mealType;

	@Column(nullable = false)
	private LocalDateTime mealDateTime;

	private String memo;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	public static Meal ofRecord(
			Member member,
			MealType mealType,
			LocalDateTime mealDateTime,
			String memo
	) {
		Meal meal = new Meal();
		meal.member = member;
		meal.mealType = mealType;
		meal.mealDateTime = mealDateTime;
		meal.memo = memo;
		return meal;
	}
}
