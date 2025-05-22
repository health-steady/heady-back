package com.heady.headyback.member.domain;

import static com.heady.headyback.member.constant.NutritionConstants.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "targets")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Target {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "member_id", nullable = false, unique = true)
	private Member member;

	@Column(nullable = false)
	private Integer fastingBloodSugar = DEFAULT_FASTING_BLOOD_SUGAR;

	@Column(nullable = false)
	private Integer postprandialBloodSugar = DEFAULT_POSTPRANDIAL_BLOOD_SUGAR;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal carbohydrate;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal protein;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal fat;

	@Column(nullable = false)
	private Integer calories;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	static Target ofCreate(
			Member member
	) {
		Target target = new Target();
		target.member = member;
		target.assignNutritionTargets();
		return target;
	}

	public void update(
			Integer fastingBloodSugar,
			Integer postprandialBloodSugar,
			BigDecimal carbohydrate,
			BigDecimal protein,
			BigDecimal fat,
			Integer calories
	) {
		this.fastingBloodSugar = fastingBloodSugar;
		this.postprandialBloodSugar = postprandialBloodSugar;
		this.carbohydrate = carbohydrate;
		this.protein = protein;
		this.fat = fat;
		this.calories = calories;
	}

	private void assignNutritionTargets() {
		BigDecimal height = member.getHeight();
		BigDecimal weight = member.getWeight();

		int age = calculateAge(member.getBirthdate());
		boolean isMale = member.isMale();

		BigDecimal bmr = calculateBmr(weight, height, age, isMale);
		BigDecimal tdee = bmr.multiply(ACTIVITY_FACTOR);

		calories = tdee.setScale(CALORIES_SCALE, ROUNDING_MODE).intValue();
		carbohydrate = kcalToGrams(
				tdee.multiply(CARBOHYDRATE_RATIO), KCAL_PER_GRAM_CARBOHYDRATE);
		protein = kcalToGrams(tdee.multiply(PROTEIN_RATIO), KCAL_PER_GRAM_PROTEIN);
		fat = kcalToGrams(tdee.multiply(FAT_RATIO), KCAL_PER_GRAM_FAT);
	}

	private BigDecimal calculateBmr(
			BigDecimal weight,
			BigDecimal height,
			int age,
			boolean isMale
	) {
		BigDecimal weightPart = weight.multiply(BMR_WEIGHT_FACTOR);
		BigDecimal heightPart = height.multiply(BMR_HEIGHT_FACTOR);
		BigDecimal agePart = BigDecimal.valueOf(age).multiply(BMR_AGE_FACTOR);
		BigDecimal genderConstant = isMale ? BMR_MALE_CONSTANT : BMR_FEMALE_CONSTANT;

		return weightPart
				.add(heightPart)
				.subtract(agePart)
				.add(genderConstant)
				.setScale(NUTRIENT_SCALE, ROUNDING_MODE);
	}

	private int calculateAge(LocalDate birthdate) {
		return LocalDate.now().getYear() - birthdate.getYear();
	}

	private BigDecimal kcalToGrams(BigDecimal kcal, int kcalPerGram) {
		return kcal.divide(
				BigDecimal.valueOf(kcalPerGram), NUTRIENT_SCALE, ROUNDING_MODE);
	}

	// TODO: 예외처리시 사용
	private <T> T requireNonNull(T value, String message) {
		if (value == null) throw new IllegalStateException(message);
		return value;
	}
}
