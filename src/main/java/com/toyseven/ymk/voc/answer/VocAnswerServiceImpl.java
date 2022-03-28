package com.toyseven.ymk.voc.answer;

import java.util.List;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.dto.voc.VocAnswerRequest;
import com.toyseven.ymk.common.dto.voc.VocAnswerResponse;
import com.toyseven.ymk.common.model.entity.VocAnswerEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;
import com.toyseven.ymk.voc.question.VocQuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocAnswerServiceImpl implements VocAnswerService {
	private final VocAnswerRepository vocAnswerRepository;
	private final VocQuestionRepository vocQuestionRepository;
	private final ObjectMapper objectMapper;
//	private final JavaMailSender mailSender;
	
	@Override
	public void save(VocAnswerRequest vocAnswerRequest) {
		vocAnswerRepository.save(vocAnswerRequest.toEntity());
		Optional<VocQuestionEntity> question = vocQuestionRepository.findById(vocAnswerRequest.getQuestionId().getId());
		question.ifPresent(inQuestion -> {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(inQuestion.getEmail());
			message.setSubject("title");
			message.setText("content");
//			mailSender.send(message);
		});
	}
	
	@Override
	public List<VocAnswerResponse> findVocAnswerByQuestionId(Long id) {
		VocQuestionEntity question = vocQuestionRepository.findById(id).get();
		List<VocAnswerEntity> answers = vocAnswerRepository.findVocAnswerByQuestionId(question);
		return objectMapper.convertValue(answers, new TypeReference<List<VocAnswerResponse>>() {});
	}
	
	@Override
	public List<VocAnswerResponse> getLatestVocQAnswers() {
		Optional<List<VocAnswerEntity>> answers = Optional.ofNullable(vocAnswerRepository.findTop10ByOrderByCreatedAtDesc());
		return objectMapper.convertValue(answers.get(), new TypeReference<List<VocAnswerResponse>>() {});
	}
}
