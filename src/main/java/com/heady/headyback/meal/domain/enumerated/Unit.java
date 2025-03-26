package com.heady.headyback.meal.domain.enumerated;

public enum Unit {
	GRAM("g"),
	MILLILITER("ml");
	private final String displayName;

	Unit(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
