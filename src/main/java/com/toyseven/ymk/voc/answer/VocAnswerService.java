package com.toyseven.ymk.voc.answer;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.VocAnswerDto;

@Transactional
public interface VocAnswerService {
	void save(VocAnswerDto.Request vocAnswerRequest);
	List<VocAnswerDto.Response> findVocAnswerByQuestionId(Long id);
	List<VocAnswerDto.Response> getLatestVocQAnswers();
}
