package com.toyseven.ymk.voc.question;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.voc.VocQuestionRequest;
import com.toyseven.ymk.common.dto.voc.VocQuestionResponse;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocQuestionServiceImpl implements VocQuestionService {
	private final VocQuestionRepository vocQuestionRepository;
	
	@Override
	public List<VocQuestionResponse> findAll() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findAll();
		return questions.stream().map(VocQuestionEntity::toVocQuestionResponse).collect(toList());
	}
	
	@Override
	public void save(VocQuestionRequest vocQuestionRequest) {
		vocQuestionRepository.save(vocQuestionRequest.toEntity());
	}
	
	@Override
	public VocQuestionResponse findVocQuestionById(Long id) {
		VocQuestionEntity question = vocQuestionRepository.findById(id).get();
		return VocQuestionResponse.builder()
				.id(question.getId())
				.category(question.getCategory().getDisplayName())
				.title(question.getTitle())
				.content(question.getContent())
				.email(question.getEmail())
				.username(question.getUsername())
				.stationId(question.getStationId().getStationId())
				.needReply(question.getNeedReply())
				.createdAt(question.getCreatedAt())
				.updatedAt(question.getUpdatedAt())
				.build();
	}
	
	@Override
	public List<VocQuestionResponse> getLatestVocQuestions() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findTop10ByOrderByCreatedAtDesc();
		return questions.stream().map(VocQuestionEntity::toVocQuestionResponse).collect(toList());
	}
	
}
