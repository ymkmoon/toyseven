package com.toyseven.ymk.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name="admin") 
public class AdminItem{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	
	@Column(name = "username", nullable = false, updatable = true, insertable = true)
	private String username;
	@Column(name = "name", nullable = false, updatable = true, insertable = true)
	private String name;
	@Column(name = "password", nullable = false, updatable = true, insertable = true)
	private String password;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = AdminRoleItem.class)
	@JoinColumn(name="role", referencedColumnName = "name", nullable = false, updatable = true, insertable = true)
	private AdminRoleItem role;
	
	@Column(name = "create_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createAt;
	@Column(name = "modified_at", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime modifiedAt;
	@Column(name = "status", nullable = false, updatable = true, insertable = false, columnDefinition = "INTEGER DEFAULT 1")
	private int status;
	
	public AdminItem() {}
	
	@Builder
	public AdminItem(String username, String name, String password, AdminRoleItem role, LocalDateTime createAt,
			LocalDateTime modifiedAt, int status) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.role = role;
		this.createAt = createAt;
		this.modifiedAt = modifiedAt;
		this.status = status;
	}
}
