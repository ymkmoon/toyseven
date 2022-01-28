package com.toyseven.ymk.voc.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

@Repository
public interface VocQuestionRepository extends JpaRepository<VocQuestionEntity, Long> {
	List<VocQuestionEntity> findTop10ByOrderByCreatedAtDesc();
}
