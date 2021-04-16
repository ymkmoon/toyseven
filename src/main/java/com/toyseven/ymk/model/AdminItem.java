package com.toyseven.ymk.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	@Column(name = "userName", nullable = false, updatable = true, insertable = true)
	private String userName;
	@Column(name = "name", nullable = false, updatable = true, insertable = true)
	private String name;
	@Column(name = "password", nullable = false, updatable = true, insertable = true)
	private String password;
	@Column(name = "role", nullable = false, updatable = true, insertable = true)
	private String role;
	@Column(name = "create_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createAt;
	@Column(name = "modified_at", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime modifiedAt;
	@Column(name = "status", nullable = false, updatable = true, insertable = false, columnDefinition = "INTEGER DEFAULT 1")
	private int status;
	
	public AdminItem() {}
	
	@Builder
	public AdminItem(String userName, String name, String password, String role, LocalDateTime createAt,
			LocalDateTime modifiedAt, int status) {
		this.userName = userName;
		this.name = name;
		this.password = password;
		this.role = role;
		this.createAt = createAt;
		this.modifiedAt = modifiedAt;
		this.status = status;
	}
}
