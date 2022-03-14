package com.toyseven.ymk.voc.answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.model.entity.VocAnswerEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;
import com.toyseven.ymk.voc.question.VocQuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocAnswerServiceImpl implements VocAnswerService {
	private final VocAnswerRepository vocAnswerRepository;
	private final VocQuestionRepository vocQuestionRepository;
	private final JavaMailSender mailSender;
	private final ModelMapper modelMapper;
	
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
		List<VocAnswerResponse> result = new ArrayList<>();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		VocQuestionEntity question = vocQuestionRepository.findById(id).get();
		Optional<List<VocAnswerEntity>> answers = Optional.ofNullable(vocAnswerRepository.findVocAnswerByQuestionId(question));
		answers.ifPresent(inAnswers -> {
			for(VocAnswerEntity answer : inAnswers) {
				result.add(modelMapper.map(answer, VocAnswerResponse.class));
			}
		});
		return result;
	}
	
	@Override
	public List<VocAnswerResponse> getLatestVocQAnswers() {
		Optional<List<VocAnswerEntity>> answers = Optional.ofNullable(vocAnswerRepository.findTop10ByOrderByCreatedAtDesc());
		List<VocAnswerResponse> result = new ArrayList<>();
		answers.ifPresent(inAnswers -> {
			for(VocAnswerEntity answer : inAnswers) {
				result.add(modelMapper.map(answer, VocAnswerResponse.class));
			}
		});
		return result;
	}
}
