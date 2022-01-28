package com.toyseven.ymk.common.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Proxy(lazy = false)
@Entity(name="voc_category")
public class VocCategoryEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5049789187171798678L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	
	
	@Column(name="name", nullable = false, updatable = false)
	private String name;
	@Column(name="display_name", nullable = false, updatable = false)
	private String displayName;
	
//	public VocCategory() {}
	
	public VocCategoryEntity(long id) {
		this.id = id;
	}

	@Builder
	public VocCategoryEntity(Long id, String name, String displayName) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
	}
	
	
	
	
}
