package com.heady.headyback.auth.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Component
@Getter
public class JwtProperties {

	protected static final String TOKEN_TYPE = "type";

	private final long accessTokenExpirationMillis;
	private final long refreshTokenExpirationMillis;

	public JwtProperties(
			@Value("${jwt.accessToken-expiration-millis}") long accessTokenExpirationMillis,
			@Value("${jwt.refreshToken-expiration-millis}") long refreshTokenExpirationMillis) {
		this.accessTokenExpirationMillis = accessTokenExpirationMillis;
		this.refreshTokenExpirationMillis = refreshTokenExpirationMillis;
	}
}
