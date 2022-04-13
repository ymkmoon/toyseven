package com.toyseven.ymk.common.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import com.toyseven.ymk.common.model.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Proxy(lazy = false)
@Entity(name="admin") 
public class AdminEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "nickname", nullable = false)
	private String nickname;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@OneToOne(targetEntity = AdminRoleEntity.class)
	@JoinColumn(name="role", referencedColumnName = "name", nullable = false)
	private AdminRoleEntity role;
	
	@Column(name = "status", nullable = false, insertable = false, columnDefinition = "INTEGER DEFAULT 1")
	private int status;
	
	public AdminEntity(long id) {
		this.id = id;
	}
	
	@Builder
	public AdminEntity(String userName, String nickname, String password, AdminRoleEntity role, LocalDateTime createAt,
			int status) {
		this.username = userName;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
		this.status = status;
	}
}
