package com.heady.headyback.user.dto.request;

public record RegisterRequest(
		// TODO : validation
		String email,
		String password,
		String name,
		String birthdate,
		String gender,
		String phone
) {
}
