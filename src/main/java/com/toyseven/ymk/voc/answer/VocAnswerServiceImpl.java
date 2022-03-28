package com.toyseven.ymk.voc.answer;

import java.util.List;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.CustomConvertValue;
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
	private final CustomConvertValue customConvertValue;
//	private final JavaMailSender mailSender;
	
	String VOC_ANSWER_RESPONSE = "VocAnswerResponse";
	
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
		return customConvertValue.vocAnswerEntityToDto(answers);
	}
	
	@Override
	public List<VocAnswerResponse> getLatestVocQAnswers() {
		List<VocAnswerEntity> answers = vocAnswerRepository.findTop10ByOrderByCreatedAtDesc();
		return customConvertValue.vocAnswerEntityToDto(answers);
	}
}
