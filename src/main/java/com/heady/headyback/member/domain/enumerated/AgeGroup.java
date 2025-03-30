package com.heady.headyback.member.domain.enumerated;

public enum AgeGroup {
	TWENTIES,
	THIRTIES,
	FORTIES_AND_ABOVE;

	public static AgeGroup fromAge(int age) {
		if (age < 30) return TWENTIES;
		if (age < 40) return THIRTIES;
		return FORTIES_AND_ABOVE;
	}
}
