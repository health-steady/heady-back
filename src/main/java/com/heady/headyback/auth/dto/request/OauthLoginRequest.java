package com.heady.headyback.auth.dto.request;

import com.heady.headyback.auth.domain.enumerated.Authority;
import com.heady.headyback.member.domain.enumerated.SocialProvider;

public record OauthLoginRequest(
		String code,
		SocialProvider socialProvider,
		Authority authority
) {
}
