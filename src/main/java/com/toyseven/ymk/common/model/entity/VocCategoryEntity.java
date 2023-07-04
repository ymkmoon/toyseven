package com.toyseven.ymk.common.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.hibernate.annotations.Proxy;

import com.toyseven.ymk.common.dto.VocCategoryDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Proxy(lazy = false)
@Entity(name="voc_category")
public class VocCategoryEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5049789187171798678L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
	private Long id;
	
	
	@Column(name="name", nullable = false, updatable = false)
	private String name;
	@Column(name="display_name", nullable = false, updatable = false)
	private String displayName;
	
//	public VocCategory() {}
	
	public VocCategoryEntity(Long id) {
		this.id = id;
	}

	@Builder
	public VocCategoryEntity(Long id, String name, String displayName) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
	}
	
	public VocCategoryDto.Response toVocCategoryResponse() {
		return VocCategoryDto.Response.builder()
				.id(id)
				.name(name)
				.displayName(displayName)
				.build();
	}
	
	
}
