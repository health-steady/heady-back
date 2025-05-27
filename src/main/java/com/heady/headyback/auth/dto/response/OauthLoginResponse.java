package com.heady.headyback.auth.dto.response;

import com.heady.headyback.auth.domain.enumerated.Authority;
import com.heady.headyback.auth.dto.AuthTokenDto;

public record OauthLoginResponse(
		String accessToken,
		Authority authority
) {
	public static OauthLoginResponse of(AuthTokenDto dto, Authority authority) {
		return new OauthLoginResponse(dto.accessToken(), authority);
	}
}
