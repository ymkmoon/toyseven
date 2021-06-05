package com.toyseven.ymk.voc.answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.voc.dto.request.VocAnswerRequest;
import com.toyseven.ymk.voc.dto.response.VocAnswerResponse;
import com.toyseven.ymk.voc.question.VocQuestionEntity;
import com.toyseven.ymk.voc.question.VocQuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocAnswerService {
	private final VocAnswerRepository vocAnswerRepository;
	private final VocQuestionRepository vocQuestionRepository;
	private final JavaMailSender mailSender;
	private final ModelMapper modelMapper;
	
	public void save(VocAnswerRequest vocAnswerRequest) {
		vocAnswerRepository.save(vocAnswerRequest.toEntity());
		Optional<VocQuestionEntity> question = vocQuestionRepository.findById(vocAnswerRequest.getQuestionId().getId());
		if(question.isPresent()) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(question.get().getEmail());
			message.setSubject("title");
			message.setText("content");
			mailSender.send(message);
		}
				
	}
	
	public VocAnswerResponse findVocAnswerByQuestionId(Long id) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Optional<VocAnswerEntity> answer = vocAnswerRepository.findVocAnswerByQuestionId(id);
		if(answer.isPresent()) {
			return modelMapper.map(answer.get(), VocAnswerResponse.class);
		}
		return null;
	}
	
	public List<VocAnswerResponse> getLatestVocQAnswers() {
		List<VocAnswerEntity> answers = vocAnswerRepository.findTop10ByOrderByCreatedAtDesc();
		List<VocAnswerResponse> result = new ArrayList<>();
		for(VocAnswerEntity answer : answers) {
			result.add(modelMapper.map(answer, VocAnswerResponse.class));
		}
		return result;
	}
}
