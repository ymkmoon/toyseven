package com.toyseven.ymk.common.util;

import java.time.Duration;

public final class Constants {

	public static final String ACCESS_TOKEN_SECRET = "acc_ess_tok_ens_ecr_et";
	public static final String REFRESH_TOKEN_SECRET = "ref_res_hto_ken_sec_ret";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final long ACCESS_TOKEN_VALIDITY = Duration.ofMinutes(30).toMillis();
    public static final long REFRESH_TOKEN_VALIDITY = Duration.ofDays(3).toMillis();
}
