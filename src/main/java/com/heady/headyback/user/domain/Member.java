package com.heady.headyback.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Table(name = "members")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Column(unique = true)
	private Email email;

	@NotNull
	private String password;

	@NotNull
	private String name;

	// TODO : 랜덤으로 만들기
	@NotNull
	private String nickname = "unknown";

	@NotNull
	private LocalDate birthdate;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	private String phone;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Status status = Status.ACTIVE;

	private String profileImageUrl;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Role role = Role.MEMBER;

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
		member.password = password;
		member.name = name;
		member.birthdate = toLocalDate(birthdate);
		member.gender = Gender.toGenderEnum(gender);
		member.phone = phone;
		return member;
	}

	private static LocalDate toLocalDate(String birthdate) {
		return LocalDate.parse(birthdate, DateTimeFormatter.BASIC_ISO_DATE);
	}
}
