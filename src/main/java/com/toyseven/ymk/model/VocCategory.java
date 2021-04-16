package com.toyseven.ymk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name="voc_category")
public class VocCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	@Column(name="name", nullable = false, updatable = false, insertable = true)
	private String name;
	@Column(name="display_name", nullable = false, updatable = false, insertable = true)
	private String displayName;
	
	public VocCategory() {}

	public VocCategory(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
	}
	
	
	
	
}
