package com.heady.headyback.auth.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtConstants {

	// ClaimNames
	public static final String TOKEN_TYPE = "token_type";
	public static final String USER_TYPE = "user_type";

	// JwtInterceptor
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String BEARER_PREFIX = "Bearer ";
	public static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

	// Request Attribute
	public static final String REQUEST_ATTR_PUBLIC_ID = "publicId";
	public static final String REQUEST_ATTR_USER_TYPE = "userType";
}
