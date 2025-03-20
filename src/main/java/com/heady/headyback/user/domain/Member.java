package com.heady.headyback.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name = "members")
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Column(unique = true)
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String name;

	@NotNull
	private String nickname;

	@NotNull
	private LocalDate birthday;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	private String phone;

	@NotNull
	private LocalDateTime createdAt;

	@NotNull
	private LocalDateTime updatedAt;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Status status;

	private String profileImageUrl;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Role role;
}
