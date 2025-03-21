package com.heady.headyback.user.controller;

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
import com.heady.headyback.user.dto.request.RegisterRequest;
import com.heady.headyback.user.service.MemberDomainService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/v1")
public class MemberController {

	private final MemberDomainService memberDomainService;

	@PostMapping
	public ResponseEntity<Void> register(
			@RequestBody @Valid RegisterRequest request
	) {

		return ResponseEntity.created(
				URI.create("/api/members/v1/" + memberDomainService.register(request))
		).build();
	}

	@GetMapping("/me")
	public ResponseEntity<?> me(@Auth Accessor accessor) {
		return ResponseEntity.ok(Map.of(
				"id", accessor.getId(),
				"authority",accessor.getAuthority()
		));
	}

}
