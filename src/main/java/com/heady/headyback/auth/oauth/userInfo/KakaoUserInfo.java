package com.heady.headyback.auth.oauth.userInfo;

import com.heady.headyback.auth.dto.response.KakaoUserInfoResponse;

public class KakaoUserInfo implements OauthUserInfo {

	private final String socialId;
	private final String email;
	private final String nickName;

	public KakaoUserInfo(KakaoUserInfoResponse response) {
		this.socialId = String.valueOf(response.id());
		this.email = response.kakaoAccount().email();
		this.nickName = response.kakaoAccount().profile().nickname();
	}

	@Override
	public String getSocialId() {
		return socialId;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getNickName() {
		return nickName;
	}
}
