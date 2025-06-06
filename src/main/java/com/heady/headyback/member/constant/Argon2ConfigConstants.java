package com.heady.headyback.member.constant;

import lombok.experimental.UtilityClass;

/**
 * Argon2 해시 알고리즘 파라미터 상수를 모아 둔 클래스입니다.
 */
@UtilityClass
public final class Argon2ConfigConstants {
	public static final int SALT_LENGTH = 16;
	public static final int HASH_LENGTH = 32;
	public static final int PARALLELISM = 1;
	public static final int MEMORY = 8192;
	public static final int ITERATIONS = 1;
}
