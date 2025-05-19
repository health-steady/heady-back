package com.heady.headyback.member.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.auth.annotation.Auth;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.member.dto.request.RegisterRequest;
import com.heady.headyback.member.dto.response.MemberResponse;
import com.heady.headyback.member.service.MemberDomainService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/v1")
public class MemberController {

	private final MemberDomainService memberDomainService;

	@PostMapping
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
		log.info("Login request received: {}", request);  // 추가
		Long memberId = memberDomainService.register(request);
		URI location = URI.create("/api/members/v1/" + memberId);
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public ResponseEntity<MemberResponse> get(@Auth Accessor accessor) {
		return ResponseEntity.ok(
				MemberResponse.of(
						memberDomainService.get(accessor)
				)
		);
	}

	@GetMapping("/me")
	public ResponseEntity<?> me(@Auth Accessor accessor) {
		return ResponseEntity.ok(Map.of(
				"id", accessor.getPublicId(),
				"authority", accessor.getAuthority()
		));
	}

}
