package com.toyseven.ymk.voc.answer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocAnswerRepository extends JpaRepository<VocAnswerEntity, Long>{
	Optional<VocAnswerEntity> findVocAnswerByQuestionId(long questionId);
	void save(VocAnswerEntity entity);
	List<VocAnswerEntity> findTop10ByOrderByCreatedAtDesc();
}
