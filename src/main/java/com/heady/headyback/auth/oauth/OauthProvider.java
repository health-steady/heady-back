package com.heady.headyback.auth.oauth;

import com.heady.headyback.auth.oauth.userInfo.OauthUserInfo;
import com.heady.headyback.member.domain.enumerated.SocialProvider;

public interface OauthProvider {
	SocialProvider getProvider();

	String getAccessToken(String code);

	OauthUserInfo getUserInfo(String accessToken);
}
