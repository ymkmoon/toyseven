package com.toyseven.ymk.common.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.toyseven.ymk.common.model.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name="refresh_token")
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	
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
	
}
