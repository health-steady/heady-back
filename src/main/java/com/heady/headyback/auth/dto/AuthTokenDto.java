package com.heady.headyback.auth.dto;

public record AuthTokenDto(
		String accessToken,
		String refreshToken
) {
	public static AuthTokenDto of(String accessToken, String refreshToken) {
		return new AuthTokenDto(accessToken, refreshToken);
	}
}
