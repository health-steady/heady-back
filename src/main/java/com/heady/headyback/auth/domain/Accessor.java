package com.heady.headyback.auth.domain;

import lombok.Getter;

@Getter
public class Accessor {

	private final Long id;
	private final Authority authority;

	public Accessor(Long memberId, Authority authority) {
		this.id = memberId;
		this.authority = authority;
	}

	public static Accessor guest() {
		return new Accessor(0L, Authority.GUEST);
	}

	public static Accessor member(final Long memberId) {
		return new Accessor(memberId, Authority.MEMBER);
	}
}
