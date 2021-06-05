package com.toyseven.ymk.voc.answer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.voc.question.VocQuestionEntity;

@Repository
public interface VocAnswerRepository extends JpaRepository<VocAnswerEntity, Long>{
	List<VocAnswerEntity> findTop10ByOrderByCreatedAtDesc();
	List<VocAnswerEntity> findVocAnswerByQuestionId(VocQuestionEntity question);
}
