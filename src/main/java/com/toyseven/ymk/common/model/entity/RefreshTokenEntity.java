package com.toyseven.ymk.common.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import com.toyseven.ymk.common.dto.TokenDto;
import com.toyseven.ymk.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name="refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenEntity extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = AdminEntity.class)
	@JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
	private AdminEntity adminId;
	
	@Column(name = "refresh_token")
	private String refreshToken;
	
	@Builder
	public RefreshTokenEntity(AdminEntity adminId, String refreshToken) {
		this.adminId = adminId;
		this.refreshToken = refreshToken;
	}
	
	public TokenDto.RefreshResponse toRefreshResponse() {
		return TokenDto.RefreshResponse.builder()
				.refreshToken(refreshToken)
				.build();
	}
	
}
