package com.toyseven.ymk.common;

import org.springframework.http.ResponseEntity;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ToysevenCommonUtil {
	
	/**
	 * response 의 null 체크와 status 체크
	 * @param response
	 * @return
	 */
	public static boolean isSuccessResponse(ResponseEntity<?> response) {
		return response != null && response.getStatusCode().is2xxSuccessful();
	}
}
