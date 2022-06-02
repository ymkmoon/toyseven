package com.toyseven.ymk.common.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CognitoDto {

	@Getter
	@NoArgsConstructor
	public static class RefreshRequest {
		@NotBlank private String refreshToken;
	}
	
	@Builder
	@Getter
	@AllArgsConstructor
	public static class RefreshResponse {
		private String accessToken;
		private String refreshToken;
	}
}
