package com.heady.headyback.member.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.member.domain.Email;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.dto.MemberDto;
import com.heady.headyback.member.dto.request.RegisterRequest;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDomainService {

	private final MemberRepository memberRepository;

	// TODO : 이메일 인증
	@Transactional
	public Long register(RegisterRequest request) {
		Email email = Email.ofCreate(request.email());
		checkDuplicationEmail(email);
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

	@Transactional(readOnly = true)
	public MemberDto get(Accessor accessor) {
		return MemberDto.of(memberRepository.findById(accessor.getId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND))
		);
	}

	private void checkDuplicationEmail(Email email) {
		if (memberRepository.existsByEmail(email)) {
			throw new CustomException(EMAIL_ALREADY_USED);
		}
	}

}
