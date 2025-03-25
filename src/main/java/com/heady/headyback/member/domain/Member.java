package com.heady.headyback.member.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.heady.headyback.member.domain.enumerated.Gender;
import com.heady.headyback.member.domain.enumerated.Role;
import com.heady.headyback.member.domain.enumerated.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(unique = true, nullable = false)
	private Email email;

	@Column(nullable = false)
	private Password password;

	@Column(nullable = false)
	private String name;

	// TODO : 랜덤으로 만들기
	@Column(nullable = false)
	private String nickname = "unknown";

	@Column(nullable = false)
	private LocalDate birthdate;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	private String phone;

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
		return member;
	}

	private static LocalDate toLocalDate(String birthdate) {
		return LocalDate.parse(birthdate, DateTimeFormatter.BASIC_ISO_DATE);
	}
}
