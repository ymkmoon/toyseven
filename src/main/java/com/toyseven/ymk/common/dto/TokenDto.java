package com.toyseven.ymk.common.dto;

import javax.validation.constraints.NotBlank;

import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.RefreshTokenEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenDto {
	
	@Getter
	@Builder
	@AllArgsConstructor
	public static class Response {
		private String accessToken;
		private String refreshToken;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	public static class Request {
		private String accessToken;
		private String refreshToken;
	}
	
	@Getter
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	public static class RefreshRequest {
		private long id;
		private AdminEntity adminId;
		@NotBlank private String refreshToken;
		
		public RefreshTokenEntity toEntity() {
			return RefreshTokenEntity.builder()
					.adminId(adminId)
					.refreshToken(refreshToken)
					.build();
		}
	}
	
}
