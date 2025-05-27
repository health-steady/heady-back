package com.heady.headyback.auth.domain;

import java.util.UUID;

import com.heady.headyback.auth.domain.enumerated.Authority;

import lombok.Getter;

@Getter
public class Accessor {

	private final UUID publicId;
	private final Authority authority;

	public Accessor(UUID publicId, Authority authority) {
		this.publicId = publicId;
		this.authority = authority;
	}

	public static Accessor guest() {
		return new Accessor(null, Authority.GUEST);
	}

	public static Accessor member(final UUID publicId) {
		return new Accessor(publicId, Authority.MEMBER);
	}
}
