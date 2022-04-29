package com.toyseven.ymk.voc.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.common.model.entity.VocCategoryEntity;

@Repository
public interface VocCategoryRepository extends JpaRepository<VocCategoryEntity, Long> {
	
}
