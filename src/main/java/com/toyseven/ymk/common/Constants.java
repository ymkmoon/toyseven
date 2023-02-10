package com.toyseven.ymk.common;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constants {

	ACCESS_TOKEN("acc_ess_tok_ens_ecr_et", Duration.ofMinutes(30).toMillis()),
	REFRESH_TOKEN("ref_res_hto_ken_sec_ret", Duration.ofDays(3).toMillis())
	;
	
	private final String secretKey;
	private final long validity;
}
