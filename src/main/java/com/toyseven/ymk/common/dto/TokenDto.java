package com.toyseven.ymk.common.dto;

import jakarta.validation.constraints.NotBlank;

import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.RefreshTokenEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {
	
	@Getter
	public static class Response {
		private String accessToken;
		private String refreshToken;

		@Builder
		public Response(String accessToken, String refreshToken) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
		}
	}
	
	@Getter
	public static class Request {
		private String accessToken;
		private String refreshToken;
		
		@Builder
		public Request(String accessToken, String refreshToken) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
		}
	}
	
	@Getter
	@NoArgsConstructor
	public static class RefreshRequest {
		@NotBlank private String refreshToken;
		
		@Builder
		public RefreshRequest(@NotBlank String refreshToken) {
			this.refreshToken = refreshToken;
		}
		
		public RefreshTokenEntity toEntity(AdminEntity admin) {
			return RefreshTokenEntity.builder()
					.adminId(admin)
					.refreshToken(refreshToken)
					.build();
		}
	}
	
	@Getter
	public static class RefreshResponse {
		private String refreshToken;

		@Builder
		public RefreshResponse(String refreshToken) {
			this.refreshToken = refreshToken;
		}
	}
}
