package com.heady.headyback.user.domain;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Gender {
	MALE("남성"), FEMALE("여성"), PRIVATE("비공개");

	private final String title;

	Gender(String title) {
		this.title = title;
	}

	public static Gender toGenderEnum(String gender) {
		return Arrays.stream(Gender.values())
				.filter(g -> g.getTitle().equals(gender))
				.findFirst()
				.orElse(Gender.PRIVATE);
	}
}
