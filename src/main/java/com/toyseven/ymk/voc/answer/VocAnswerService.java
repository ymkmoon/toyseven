package com.toyseven.ymk.voc.answer;

import java.util.List;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.voc.dto.request.VocAnswerRequest;
import com.toyseven.ymk.voc.question.VocQuestion;
import com.toyseven.ymk.voc.question.VocQuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocAnswerService {
	private final VocAnswerRepository vocAnswerRepository;
	private final VocQuestionRepository vocQuestionRepository;
	private final JavaMailSender mailSender;
	
	public List<VocAnswer> findAll() {
		return vocAnswerRepository.findAll();
	}
	
	public void save(VocAnswerRequest vocAnswerRequest) {
		vocAnswerRepository.save(vocAnswerRequest);
		
		Optional<VocQuestion> question = vocQuestionRepository.findById(vocAnswerRequest.getQuestion_id());
		if(question.isPresent()) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(question.get().getEmail());
			message.setSubject("title");
			message.setText("content");
			mailSender.send(message);
		}
				
	}
	
	public Optional<List<VocAnswer>> findByVocAnswer(Optional<VocQuestion> vocQuestion) {
		return vocAnswerRepository.findByQuestionId(vocQuestion);
	}
}
