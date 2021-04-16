package com.toyseven.ymk.model;

import java.io.Serializable;

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
@Entity(name="admin_role")
public class AdminRoleItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -244868210540276880L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	
	@Column(name = "name", nullable = false, updatable = false, insertable = true)
	private String name;
	
	@Column(name = "display_name", nullable = false, updatable = false, insertable = true)
	private String displayName;
	
	public AdminRoleItem() {}

	@Builder
	public AdminRoleItem(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
	}
}
