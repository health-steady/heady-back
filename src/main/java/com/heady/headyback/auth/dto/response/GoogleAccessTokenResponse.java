package com.heady.headyback.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleAccessTokenResponse(
		@JsonProperty("access_token") String accessToken,
		@JsonProperty("expires_in") int expiresIn,
		@JsonProperty("token_type") String tokenType,
		@JsonProperty("scope") String scope,
		@JsonProperty("id_token") String refreshToken
) {
}
