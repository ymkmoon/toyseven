package com.toyseven.ymk.voc.question;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VocQuestionService {
	List<VocQuestionResponse> findAll();
	void save(VocQuestionRequest vocQuestionRequest);
	VocQuestionResponse findVocQuestionById(Long id);
	List<VocQuestionResponse> getLatestVocQuestions();
}
