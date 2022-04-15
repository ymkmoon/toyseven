package com.toyseven.ymk.common.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
 
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="admin_role")
public class AdminRoleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -244868210540276880L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
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
