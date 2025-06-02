package com.heady.headyback.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.auth.dto.AuthTokenDto;
import com.heady.headyback.auth.dto.request.LoginRequest;
import com.heady.headyback.auth.dto.request.OauthLoginRequest;
import com.heady.headyback.auth.dto.response.LoginResponse;
import com.heady.headyback.auth.dto.response.OauthLoginResponse;
import com.heady.headyback.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(
			@RequestBody @Valid LoginRequest request
	) {

		AuthTokenDto token = authService.login(request);
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, token.refreshToken())
				.body(LoginResponse.of(token.accessToken()));
	}

	@PostMapping("/oauth")
	public ResponseEntity<OauthLoginResponse> oauthLogin(
			@RequestBody OauthLoginRequest request
	) {

		return ResponseEntity.ok().body(
				OauthLoginResponse.of(
						authService.oauthLogin(request), request.authority()
				)
		);
	}
}
