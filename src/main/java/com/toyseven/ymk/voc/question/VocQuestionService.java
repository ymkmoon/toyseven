package com.toyseven.ymk.voc.question;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.VocQuestionDto;

@Transactional
public interface VocQuestionService {
	List<VocQuestionDto.Response> findAll();
	VocQuestionDto.Response save(VocQuestionDto.Request vocQuestionRequest, String username);
	VocQuestionDto.Response findVocQuestionById(Long id);
	List<VocQuestionDto.Response> getLatestVocQuestions();
}
