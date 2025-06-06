package com.heady.headyback.auth.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public CompletableFuture<ResponseEntity<LoginResponse>> login(
			@RequestBody @Valid LoginRequest request
	) {

		return authService.login(request)
				.thenApply(tokenDto -> ResponseEntity.ok()
						.header(HttpHeaders.SET_COOKIE, tokenDto.refreshToken())
						.body(LoginResponse.of(tokenDto.accessToken())));
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
