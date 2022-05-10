package com.toyseven.ymk.voc.answer;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.VocAnswerDto;
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
//	private final JavaMailSender mailSender;
	
	@Override
	public VocAnswerDto.Response save(VocAnswerDto.Request vocAnswerRequest) {
		VocQuestionEntity question = vocQuestionRepository.findById(vocAnswerRequest.getQuestionId())
				.orElseThrow(() -> new BusinessException("해당 Question 조회가 불가능 합니다.", ErrorCode.QUESTION_IS_NOT_EXIST));
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		AdminEntity admin = adminRepository.findAccountByUsername(username)
				.orElseThrow(() -> new BusinessException("해당 Admin 조회가 불가능 합니다.", ErrorCode.BAD_REQUEST));
		
		VocAnswerEntity answer = vocAnswerRepository.save(vocAnswerRequest.toEntity(question, admin));
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(question.getEmail());
		message.setSubject("title");
		message.setText("content");
		// mailSender.send(message);
		
		return answer.toVocAnswerResponse();
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
