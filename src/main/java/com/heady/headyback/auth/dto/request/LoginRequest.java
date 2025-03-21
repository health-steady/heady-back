package com.heady.headyback.auth.dto.request;

public record LoginRequest(
		String email,
		String password
) {
}
