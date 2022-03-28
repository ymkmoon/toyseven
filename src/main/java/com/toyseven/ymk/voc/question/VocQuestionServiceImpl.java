package com.toyseven.ymk.voc.question;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.CustomConvertValue;
import com.toyseven.ymk.common.dto.voc.VocQuestionRequest;
import com.toyseven.ymk.common.dto.voc.VocQuestionResponse;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocQuestionServiceImpl implements VocQuestionService {
	private final VocQuestionRepository vocQuestionRepository;
	private final CustomConvertValue customConvertValue;
	
	@Override
	public List<VocQuestionResponse> findAll() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findAll();
		return customConvertValue.vocQuestionEntityToDto(questions);
	}
	
	@Override
	public void save(VocQuestionRequest vocQuestionRequest) {
		vocQuestionRepository.save(vocQuestionRequest.toEntity());
	}
	
	@Override
	public VocQuestionResponse findVocQuestionById(Long id) {
		VocQuestionEntity question = vocQuestionRepository.findById(id).get();
		return customConvertValue.vocQuestionEntityToDto(question);
	}
	
	@Override
	public List<VocQuestionResponse> getLatestVocQuestions() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findTop10ByOrderByCreatedAtDesc();
		return customConvertValue.vocQuestionEntityToDto(questions);
	}
	
}
