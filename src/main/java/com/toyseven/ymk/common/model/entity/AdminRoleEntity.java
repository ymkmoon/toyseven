package com.toyseven.ymk.common.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
 
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="admin_role")
public class AdminRoleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -244868210540276880L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
	private Long id;
	
	@Column(name = "name", nullable = false, updatable = false)
	private String name;
	
	@Column(name = "display_name", nullable = false, updatable = false)
	private String displayName;

	@Builder
	public AdminRoleEntity(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
	}
}
