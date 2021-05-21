package com.toyseven.ymk.voc.answer;

import java.util.List;
import java.util.Optional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.handler.MailHandler;
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
		StringBuffer content = new StringBuffer();
		if(question.isPresent()) {
			MailHandler mailHandler;
			try {
				mailHandler = new MailHandler(mailSender);
				mailHandler.setTo(question.get().getEmail());
				mailHandler.setSubject("따릉이 서비스 문의 글에 답변이 등록 되었습니다.");
				mailHandler.setText(content.append(question.get().getUsername()+"님 아래 링크를 눌러 바로 확인하세요 ! <br/>")
								.append("<a href='http://218.153.121.205:8000/toyseven/voc/search/")
								.append(vocAnswerRequest.getQuestion_id())
								.append("'>답변 확인</a>")
								.toString(), true);
				mailHandler.setInline("sample-img", "static/sample.png");
				mailHandler.send();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Optional<List<VocAnswer>> findByVocAnswer(Optional<VocQuestion> vocQuestion) {
		return vocAnswerRepository.findByQuestionId(vocQuestion);
	}
}
