package com.heady.headyback.user.service;

import static com.heady.headyback.user.exception.MemberExceptionCode.*;

import org.springframework.stereotype.Service;

import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.user.domain.Member;
import com.heady.headyback.user.dto.request.RegisterRequest;
import com.heady.headyback.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDomainService {

	private final MemberRepository memberRepository;

	// TODO : 이메일 인증
	public Long register(
			RegisterRequest request
	) {
		checkDuplicationEmail(request.email());
		return memberRepository.save(
				Member.ofRegister(
						request.email(),
						request.password(),
						request.name(),
						request.birthdate(),
						request.gender(),
						request.phone()
				)
		).getId();
	}

	private void checkDuplicationEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new CustomException(EMAIL_ALREADY_USED);
		}
	}

}
