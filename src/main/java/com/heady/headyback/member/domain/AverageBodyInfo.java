package com.heady.headyback.member.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import com.heady.headyback.member.domain.enumerated.AgeGroup;
import com.heady.headyback.member.domain.enumerated.Gender;

import lombok.Getter;

public class AverageBodyInfo {

	@Getter
	public static final class Body {
		private final BigDecimal height; // cm
		private final BigDecimal weight; // kg

		public Body(BigDecimal height, BigDecimal weight) {
			this.height = height;
			this.weight = weight;
		}
	}

	public static final Map<Gender, Map<AgeGroup, Body>> DATA =
			new EnumMap<>(Gender.class);

	static {
		Map<AgeGroup, Body> maleData = createMaleBodyData();
		Map<AgeGroup, Body> femaleData = createFemaleBodyData();

		DATA.put(Gender.MALE, Collections.unmodifiableMap(maleData));
		DATA.put(Gender.FEMALE, Collections.unmodifiableMap(femaleData));
	}

	private static Map<AgeGroup, Body> createMaleBodyData() {
		Map<AgeGroup, Body> data = new EnumMap<>(AgeGroup.class);
		data.put(AgeGroup.TWENTIES, new Body(height("174.0"), weight("72.0")));
		data.put(AgeGroup.THIRTIES, new Body(height("172.5"), weight("74.0")));
		data.put(AgeGroup.FORTIES_AND_ABOVE, new Body(height("170.0"), weight("73.0")));
		return data;
	}

	private static Map<AgeGroup, Body> createFemaleBodyData() {
		Map<AgeGroup, Body> data = new EnumMap<>(AgeGroup.class);
		data.put(AgeGroup.TWENTIES, new Body(height("161.0"), weight("57.0")));
		data.put(AgeGroup.THIRTIES, new Body(height("160.5"), weight("58.0")));
		data.put(AgeGroup.FORTIES_AND_ABOVE, new Body(height("159.0"), weight("59.0")));
		return data;
	}

	private static BigDecimal height(String value) {
		return new BigDecimal(value);
	}

	private static BigDecimal weight(String value) {
		return new BigDecimal(value);
	}

	public static Body getAverage(Gender gender, int age) {
		AgeGroup group = AgeGroup.fromAge(age);
		return DATA.get(gender).get(group);
	}
}
