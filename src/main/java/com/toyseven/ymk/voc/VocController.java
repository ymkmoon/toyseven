package com.toyseven.ymk.voc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.common.dto.VocAnswerDto;
import com.toyseven.ymk.common.dto.VocCategoryDto;
import com.toyseven.ymk.common.dto.VocQuestionDto;
import com.toyseven.ymk.voc.answer.VocAnswerService;
import com.toyseven.ymk.voc.category.VocCategoryService;
import com.toyseven.ymk.voc.question.VocQuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("voc")
public class VocController {
	
	private final VocQuestionService vocQuestionService;
	private final VocAnswerService vocAnswerService;
	private final VocCategoryService vocCategoryService;
	 
	@GetMapping()
	public ResponseEntity<List<VocQuestionDto.Response>> getVocQuestions() {
		return new ResponseEntity<>(vocQuestionService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<VocQuestionDto.Response> saveVocQuestion(
			@RequestBody @Valid VocQuestionDto.Request vocQuestionRequest) {
		return new ResponseEntity<>(vocQuestionService.save(vocQuestionRequest), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/search/{id}")
	public ResponseEntity<Map<String, Object>> getVocQuestion(
			@PathVariable(value = "id") Long id) {
		Map<String, Object> voc = new HashMap<>();
		VocQuestionDto.Response question = vocQuestionService.findVocQuestionById(id);
		voc.put("question", question);
		if(question != null) {
			List<VocAnswerDto.Response> answer =  vocAnswerService.findVocAnswerByQuestionId(question.getId());
			if(answer != null) {
				voc.put("answer", answer);
			}
		}
		
		return new ResponseEntity<>(voc, HttpStatus.OK);
	}
	
	@PostMapping(value = "/answer")
	public ResponseEntity<VocAnswerDto.Response> saveVocAnswer(
			@RequestBody @Valid VocAnswerDto.Request vocAnswerRequest) {
		return new ResponseEntity<>(vocAnswerService.save(vocAnswerRequest), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/questions")
	public ResponseEntity<List<VocQuestionDto.Response>> getLatestVocQuestions() {
		return new ResponseEntity<>(vocQuestionService.getLatestVocQuestions(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/answers")
	public ResponseEntity<List<VocAnswerDto.Response>> getLatestVocQAnswers() {
		return new ResponseEntity<>(vocAnswerService.getLatestVocQAnswers(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/category")
	public ResponseEntity<List<VocCategoryDto.Response>> getCategories() {
		return new ResponseEntity<>(vocCategoryService.getVocCategory(), HttpStatus.OK);
	}
	
	
	
}
