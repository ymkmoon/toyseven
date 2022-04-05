package com.toyseven.ymk.common.dto;

import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.RefreshTokenEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RefreshTokenDto {

	@Getter
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	public static class Request {
		private long id;
		private AdminEntity adminId;
		private String refreshToken;
		
		public RefreshTokenEntity toEntity() {
			return RefreshTokenEntity.builder()
					.adminId(adminId)
					.refreshToken(refreshToken)
					.build();
		}
	}
	
}
