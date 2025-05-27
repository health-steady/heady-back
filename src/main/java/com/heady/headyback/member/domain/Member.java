package com.heady.headyback.member.domain;

import static com.heady.headyback.member.domain.enumerated.Gender.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.heady.headyback.member.domain.enumerated.Gender;
import com.heady.headyback.member.domain.enumerated.Role;
import com.heady.headyback.member.domain.enumerated.SocialProvider;
import com.heady.headyback.member.domain.enumerated.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "members")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "BINARY(16)", unique = true, nullable = false, updatable = false)
	private UUID publicId;

	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
	private Target target;

	@Column(unique = true, nullable = false)
	private Email email;

	@Column(nullable = false)
	private Password password;

	@Column(nullable = false)
	private String name;

	@Column(length = 50)
	@Enumerated(value = EnumType.STRING)
	private SocialProvider socialProvider;

	@Column(unique = true)
	private String socialId;

	@Column(nullable = false)
	private String nickname = "unknown";

	@Column
	private LocalDate birthdate;

	@Column
	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	private String phone;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal height;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal weight;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Status status = Status.ACTIVE;

	private String profileImageUrl;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role = Role.MEMBER;

	private boolean isDeleted = false;

	public static Member ofRegister(
			String email,
			String password,
			String name,
			String birthdate,
			String gender,
			String phone
	) {
		Member member = new Member();
		member.email = Email.ofCreate(email);
		member.password = Password.ofCreate(password);
		member.name = name;
		member.birthdate = toLocalDate(birthdate);
		member.gender = Gender.toGenderEnum(gender);
		member.phone = phone;
		member.assignAverageHeightAndWeight();
		member.target = Target.ofCreate(member);
		return member;
	}

	public static Member signUpWithOauth(
			String email,
			String name,
			SocialProvider socialProvider,
			String socialId
	) {
		Member member = new Member();
		member.email = Email.ofCreate(email);
		member.name = name;
		member.socialProvider = socialProvider;
		member.socialId = socialId;
		member.gender = PRIVATE;
		member.assignAverageHeightAndWeight();
		member.target = Target.ofCreate(member);
		return member;
	}

	public Member update(
			String name,
			BigDecimal height,
			BigDecimal weight,
			Integer fastingBloodSugar,
			Integer postprandialBloodSugar,
			BigDecimal carbohydrate,
			BigDecimal protein,
			BigDecimal fat,
			Integer calories
	) {
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.target.update(
				fastingBloodSugar,
				postprandialBloodSugar,
				carbohydrate,
				protein,
				fat,
				calories
		);
		return this;
	}

	public boolean isMale() {
		return gender == MALE;
	}

	private void assignAverageHeightAndWeight() {
		int age = Optional.ofNullable(this.birthdate)
				.map(d -> Period.between(d, LocalDate.now()).getYears())
				.orElse(25);
		AverageBodyInfo.Body body = AverageBodyInfo.getAverage(this.gender, age);
		height = body.getHeight();
		weight = body.getWeight();
	}

	private static LocalDate toLocalDate(String birthdate) {
		return LocalDate.parse(birthdate, DateTimeFormatter.BASIC_ISO_DATE);
	}

	@PrePersist
	private void generateUuid() {
		if (this.publicId == null) {
			this.publicId = UUID.randomUUID();
		}
	}
}
