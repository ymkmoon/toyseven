package com.toyseven.ymk.voc.answer;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.jms.JMSException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.SqsComponent;
import com.toyseven.ymk.common.dto.VocAnswerDto;
import com.toyseven.ymk.common.dto.VocAnswerDto.Response;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.exception.BusinessException;
import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.VocAnswerEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;
import com.toyseven.ymk.jwt.AdminRepository;
import com.toyseven.ymk.voc.question.VocQuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocAnswerServiceImpl implements VocAnswerService {
	private final VocAnswerRepository vocAnswerRepository;
	private final VocQuestionRepository vocQuestionRepository;
	private final AdminRepository adminRepository;
	
	private final SqsComponent sqsComponent;
	
	@Override
	public VocAnswerDto.Response saveVocAnswer(VocAnswerDto.Request vocAnswerRequest) {
		VocQuestionEntity question = vocQuestionRepository.findById(vocAnswerRequest.getQuestionId())
				.orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_IS_NOT_EXIST.getDetail(), ErrorCode.QUESTION_IS_NOT_EXIST));
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		AdminEntity admin = Optional.ofNullable(adminRepository.findAccountByUsername(username))
				.orElseThrow(() -> new BusinessException(ErrorCode.ADMIN_IS_NOT_EXIST.getDetail(), ErrorCode.ADMIN_IS_NOT_EXIST));
		
		boolean isActive = true;
		VocAnswerEntity answer = vocAnswerRepository.save(vocAnswerRequest.toEntity(question, admin, isActive));
		
		try {
			String queueName = "testUser";
			sqsComponent.sendMessage(queueName, vocAnswerRequest);
//			sqsComponent.receive("testQueue");
		} catch (JMSException e) {
			
		}
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(question.getEmail());
		message.setSubject("title");
		message.setText("content");
		// mailSender.send(message);
		
		return answer.toVocAnswerResponse();
	}
	
	@Override
	public List<VocAnswerDto.Response> getVocAnswerByQuestionId(Long id) {
		VocQuestionEntity question = vocQuestionRepository.findById(id)
				.orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_IS_NOT_EXIST.getDetail(), ErrorCode.QUESTION_IS_NOT_EXIST));
		List<VocAnswerEntity> answers = vocAnswerRepository.findVocAnswerByQuestionId(question);
		return answers.stream().map(VocAnswerEntity::toVocAnswerResponse).collect(toList());
	}
	
	@Override
	public List<VocAnswerDto.Response> getLatestVocQAnswers() {
		List<VocAnswerEntity> answers = vocAnswerRepository.findTop10ByOrderByCreatedAtDesc();
		return answers.stream().map(VocAnswerEntity::toVocAnswerResponse).collect(toList());
	}

	@Override
	public Response updateVocAnswer(VocAnswerDto.UpdateRequest vocAnswerUpdateRequest) {
		VocAnswerEntity answer = vocAnswerRepository.findById(vocAnswerUpdateRequest.getId())
				.orElseThrow(() -> new BusinessException(ErrorCode.ANSWER_IS_NOT_EXIST.getDetail(), ErrorCode.ANSWER_IS_NOT_EXIST));
		
		answer.update(vocAnswerUpdateRequest.getContent());
		
		return answer.toVocAnswerResponse();
	}
	
}
