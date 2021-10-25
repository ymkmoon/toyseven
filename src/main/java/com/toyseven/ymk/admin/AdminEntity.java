package com.toyseven.ymk.admin;

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

@Getter
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Proxy(lazy = false)
@Entity(name="admin") 
public class AdminEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "password", nullable = false)
	private String password;
	
	@OneToOne(targetEntity = AdminRoleEntity.class)
	@JoinColumn(name="role", referencedColumnName = "name", nullable = false)
	private AdminRoleEntity role;
	
	@Column(name = "modified_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime modifiedAt;
	@Column(name = "status", nullable = false, insertable = false, columnDefinition = "INTEGER DEFAULT 1")
	private int status;
	
	public AdminEntity() {}
	
	public AdminEntity(long id) {
		this.id = id;
	}
	
	@Builder
	public AdminEntity(String userName, String name, String password, AdminRoleEntity role, LocalDateTime createAt,
			LocalDateTime modifiedAt, int status) {
		this.username = userName;
		this.name = name;
		this.password = password;
		this.role = role;
		this.modifiedAt = modifiedAt;
		this.status = status;
	}
}
