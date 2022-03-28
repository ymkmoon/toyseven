package com.toyseven.ymk.voc.answer;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.voc.VocAnswerRequest;
import com.toyseven.ymk.common.dto.voc.VocAnswerResponse;

@Transactional
public interface VocAnswerService {
	void save(VocAnswerRequest vocAnswerRequest);
	List<VocAnswerResponse> findVocAnswerByQuestionId(Long id);
	List<VocAnswerResponse> getLatestVocQAnswers();
}
