package com.toyseven.ymk.common.util;

import java.time.Duration;

public final class Constants {

	public static final String TOKEN_SECRET = "dd_a_reu-ng2_secret";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final long ACCESS_TOKEN_VALIDITY = Duration.ofMinutes(30).toMillis();
    public static final long REFRESH_TOKEN_VALIDITY = Duration.ofDays(3).toMillis();
}
