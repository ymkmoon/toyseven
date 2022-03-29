package com.toyseven.ymk.voc.answer;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.VocAnswerDto;
import com.toyseven.ymk.common.model.entity.VocAnswerEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;
import com.toyseven.ymk.voc.question.VocQuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocAnswerServiceImpl implements VocAnswerService {
	private final VocAnswerRepository vocAnswerRepository;
	private final VocQuestionRepository vocQuestionRepository;
//	private final JavaMailSender mailSender;
	
	@Override
	public void save(VocAnswerDto.Request vocAnswerRequest) {
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
	public List<VocAnswerDto.Response> findVocAnswerByQuestionId(Long id) {
		VocQuestionEntity question = vocQuestionRepository.findById(id).get();
		List<VocAnswerEntity> answers = vocAnswerRepository.findVocAnswerByQuestionId(question);
		return answers.stream().map(VocAnswerEntity::toVocAnswerResponse).collect(toList());
	}
	
	@Override
	public List<VocAnswerDto.Response> getLatestVocQAnswers() {
		List<VocAnswerEntity> answers = vocAnswerRepository.findTop10ByOrderByCreatedAtDesc();
		return answers.stream().map(VocAnswerEntity::toVocAnswerResponse).collect(toList());
	}
}
