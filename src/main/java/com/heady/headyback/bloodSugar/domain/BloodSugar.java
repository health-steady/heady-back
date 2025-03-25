package com.heady.headyback.bloodSugar.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.heady.headyback.bloodSugar.domain.enumerated.MeasurementTimeType;
import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.user.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "blood_sugar")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BloodSugar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Meal meal;

	@Column(nullable = false)
	private Integer level;

	@Column(nullable = false)
	private LocalDateTime measuredAt;

	@Column(nullable = false)
	private MeasurementTimeType measurementTimeType;

	private String memo;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	public static BloodSugar ofRecord(
			Member member,
			Meal meal,
			Integer level,
			LocalDateTime measuredAt,
			String measurementTimeType,
			String memo
	) {
		BloodSugar bloodSugar = new BloodSugar();
		bloodSugar.member = member;
		bloodSugar.meal = meal;
		bloodSugar.level = level;
		bloodSugar.measuredAt = measuredAt;
		bloodSugar.measurementTimeType =
				MeasurementTimeType.getMappedMeasurementTimeType(measurementTimeType);
		bloodSugar.memo = memo;
		return bloodSugar;
	}
}
