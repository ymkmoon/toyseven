package com.toyseven.ymk.voc.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocQuestionServiceImpl implements VocQuestionService {
	private final VocQuestionRepository vocQuestionRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public List<VocQuestionResponse> findAll() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<VocQuestionResponse> result = new ArrayList<>();
		
		Optional<List<VocQuestionEntity>> questions = Optional.ofNullable(vocQuestionRepository.findAll());
		questions.ifPresent(inQuestions -> {
			for(VocQuestionEntity question : inQuestions ) {
				result.add(modelMapper.map(question, VocQuestionResponse.class));
			}
		});
		return result;
	}
	
	@Override
	public void save(VocQuestionRequest vocQuestionRequest) {
		vocQuestionRepository.save(vocQuestionRequest.toEntity());
	}
	
	@Override
	public VocQuestionResponse findVocQuestionById(Long id) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Optional<VocQuestionEntity> question = vocQuestionRepository.findById(id);
		return question.isPresent() ? modelMapper.map(question.get(), VocQuestionResponse.class) : null; // 코드 리펙토링 필요
	}
	
	@Override
	public List<VocQuestionResponse> getLatestVocQuestions() {
		List<VocQuestionResponse> result = new ArrayList<>();
		Optional<List<VocQuestionEntity>> questions = Optional.ofNullable(vocQuestionRepository.findTop10ByOrderByCreatedAtDesc());
		questions.ifPresent(inQuestions -> {
			for(VocQuestionEntity question : inQuestions) {
				result.add(modelMapper.map(question, VocQuestionResponse.class));
			}
		});
		return result;
	}
	
}
