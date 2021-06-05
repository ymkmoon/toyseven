package com.toyseven.ymk.voc.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocQuestionRepository extends JpaRepository<VocQuestionEntity, Long> {
	void save(VocQuestionEntity vocQuestion);

	List<VocQuestionEntity> findTop10ByOrderByCreatedAtDesc();
}
