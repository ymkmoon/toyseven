package com.toyseven.ymk.voc.question;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocQuestionServiceImpl implements VocQuestionService {
	private final VocQuestionRepository vocQuestionRepository;
	private final ObjectMapper objectMapper;
	// private final ModelMapper modelMapper;
	
	@Override
	public List<VocQuestionResponse> findAll() {
		Optional<List<VocQuestionEntity>> questions = Optional.ofNullable(vocQuestionRepository.findAll());
		return objectMapper.convertValue(questions.get(), new TypeReference<List<VocQuestionResponse>>() {});
	}
	
	@Override
	public void save(VocQuestionRequest vocQuestionRequest) {
		vocQuestionRepository.save(vocQuestionRequest.toEntity());
	}
	
	@Override
	public VocQuestionResponse findVocQuestionById(Long id) {
		Optional<VocQuestionEntity> question = vocQuestionRepository.findById(id);
		return objectMapper.convertValue(question.get(), new TypeReference<VocQuestionResponse>() {});
	}
	
	@Override
	public List<VocQuestionResponse> getLatestVocQuestions() {
		Optional<List<VocQuestionEntity>> questions = Optional.ofNullable(vocQuestionRepository.findTop10ByOrderByCreatedAtDesc());
		return objectMapper.convertValue(questions.get(), new TypeReference<List<VocQuestionResponse>>() {});
	}
	
}
