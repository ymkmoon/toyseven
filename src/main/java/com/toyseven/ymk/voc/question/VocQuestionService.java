package com.toyseven.ymk.voc.question;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.VocQuestionDto;

@Transactional(readOnly = true)
public interface VocQuestionService {
	List<VocQuestionDto.Response> getAllVocQuestions();
	List<VocQuestionDto.Response> getLatestVocQuestions();
	VocQuestionDto.Response getVocQuestionById(Long id);
	@Transactional VocQuestionDto.Response saveVocQuestion(VocQuestionDto.Request vocQuestionRequest, String username);
	@Transactional VocQuestionDto.Response updateVocQuestion(String username, VocQuestionDto.UpdateRequest vocQuestionUpdateRequest);
}
