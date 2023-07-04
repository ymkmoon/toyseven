package com.toyseven.ymk.common.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CognitoDto {

	@Getter
	@NoArgsConstructor
	public static class RefreshRequest {
		@NotBlank private String refreshToken;
	}
	
	@Getter
	public static class RefreshResponse {
		private String accessToken;
		private String refreshToken;
		
		@Builder
		public RefreshResponse(String accessToken, String refreshToken) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
		}
	}
}
