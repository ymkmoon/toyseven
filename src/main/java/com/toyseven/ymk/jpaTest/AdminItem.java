package com.toyseven.ymk.jpaTest;

import java.sql.Timestamp;

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
public class AdminItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String userName;
	private String name;
	private String password;
	private String role;
	@Column(name = "create_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp create_at;
	@Column(name = "modified_at", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp modified_at;
	private int status;
	
	public AdminItem() {}
	
	@Builder
	public AdminItem(String userName, String name, String password, String role, Timestamp create_at,
			Timestamp modified_at, int status) {
		this.userName = userName;
		this.name = name;
		this.password = password;
		this.role = role;
		this.create_at = create_at;
		this.modified_at = modified_at;
		this.status = status;
	}
}
