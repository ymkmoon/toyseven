package com.toyseven.ymk.voc.question;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.voc.VocQuestionDto;

@Transactional
public interface VocQuestionService {
	List<VocQuestionDto.Response> findAll();
	void save(VocQuestionDto.Request vocQuestionRequest);
	VocQuestionDto.Response findVocQuestionById(Long id);
	List<VocQuestionDto.Response> getLatestVocQuestions();
}
