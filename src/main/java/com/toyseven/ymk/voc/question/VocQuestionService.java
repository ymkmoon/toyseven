package com.toyseven.ymk.voc.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.voc.dto.request.VocQuestionRequest;
import com.toyseven.ymk.voc.dto.response.VocQuestionResponse;
import com.toyseven.ymk.voc.entity.VocQuestionEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocQuestionService {
	private final VocQuestionRepository vocQuestionRepository;
	private final ModelMapper modelMapper;
	
	public List<VocQuestionResponse> findAll() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<VocQuestionEntity> questions = vocQuestionRepository.findAll();
		List<VocQuestionResponse> result = new ArrayList<>();
		for(VocQuestionEntity question : questions ) {
			result.add(modelMapper.map(question, VocQuestionResponse.class));
		}
		return result;
	}
	
	public void save(VocQuestionRequest vocQuestionRequest) {
		vocQuestionRepository.save(vocQuestionRequest.toEntity());
	}
	
	public VocQuestionResponse findVocQuestionById(Long id) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Optional<VocQuestionEntity> question = vocQuestionRepository.findById(id);
		if(question.isPresent()) {
			return modelMapper.map(question.get(), VocQuestionResponse.class);
		}
		return null;
	}
	
	public List<VocQuestionResponse> getVocQuestionsTop10() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findTop10ByOrderByCreatedAtDesc();
		List<VocQuestionResponse> result = new ArrayList<>();
		for(VocQuestionEntity question : questions) {
			result.add(modelMapper.map(question, VocQuestionResponse.class));
		}
		return result;
	}
	
}
