package com.toyseven.ymk.voc.answer;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VocAnswerService {
	void save(VocAnswerRequest vocAnswerRequest);
	List<VocAnswerResponse> findVocAnswerByQuestionId(Long id);
	List<VocAnswerResponse> getLatestVocQAnswers();
}
