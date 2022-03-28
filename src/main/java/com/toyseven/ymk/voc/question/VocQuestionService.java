package com.toyseven.ymk.voc.question;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.voc.VocQuestionRequest;
import com.toyseven.ymk.common.dto.voc.VocQuestionResponse;

@Transactional
public interface VocQuestionService {
	List<VocQuestionResponse> findAll();
	void save(VocQuestionRequest vocQuestionRequest);
	VocQuestionResponse findVocQuestionById(Long id);
	List<VocQuestionResponse> getLatestVocQuestions();
}
