// package com.heady.headyback.auth.oauth.userInfo;
//
// import com.heady.headyback.auth.dto.response.GoogleUserInfoResponse;
//
// public class GoogleUserInfo implements OauthUserInfo {
//
// 	private final String socialId;
// 	private final String email;
// 	private final String nickName;
//
// 	public GoogleUserInfo(GoogleUserInfoResponse response) {
// 		this.socialId = response.id();
// 		this.email = response.email();
// 		this.nickName = response.name();
// 	}
//
// 	@Override
// 	public String getSocialId() {
// 		return socialId;
// 	}
//
// 	@Override
// 	public String getEmail() {
// 		return email;
// 	}
//
// 	@Override
// 	public String getNickName() {
// 		return nickName;
// 	}
// }
