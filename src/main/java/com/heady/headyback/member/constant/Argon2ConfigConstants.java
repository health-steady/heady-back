package com.heady.headyback.member.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Argon2ConfigConstants {
	public static final int SALT_LENGTH = 16;
	public static final int HASH_LENGTH = 32;
	public static final int PARALLELISM = 2;
	public static final int MEMORY = 8192;
	public static final int ITERATIONS = 1;
}
