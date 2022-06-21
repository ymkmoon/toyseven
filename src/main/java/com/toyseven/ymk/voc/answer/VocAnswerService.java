package com.toyseven.ymk.voc.answer;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.VocAnswerDto;

@Transactional(readOnly = true)
public interface VocAnswerService {
	@Transactional VocAnswerDto.Response saveVocAnswer(VocAnswerDto.Request vocAnswerRequest);
	List<VocAnswerDto.Response> getVocAnswerByQuestionId(Long id);
	List<VocAnswerDto.Response> getLatestVocQAnswers();
}
