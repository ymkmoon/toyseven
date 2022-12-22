package com.toyseven.ymk.voc;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.common.OffsetBasedPageRequest;
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
	public ResponseEntity<List<VocQuestionDto.Response>> getVocQuestions(
			@RequestParam(name="offset") @NotNull long offset,
    		@RequestParam(name="limit") @NotNull int limit) {
		Pageable pageable = new OffsetBasedPageRequest(offset, limit);
		return new ResponseEntity<>(vocQuestionService.getAllVocQuestions(pageable), HttpStatus.OK);
	}
	
	@PostMapping(value = "/question")
	public ResponseEntity<VocQuestionDto.Response> saveVocQuestion(
			Principal principal, 
			@RequestBody @Valid VocQuestionDto.Request vocQuestionRequest) {
		String username = principal.getName();
		return new ResponseEntity<>(vocQuestionService.saveVocQuestion(vocQuestionRequest, username), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/search/{id}")
	public ResponseEntity<Map<String, Object>> getVocQuestion(
			@PathVariable(value = "id") Long id) {
		Map<String, Object> voc = new HashMap<>();
		VocQuestionDto.Response question = vocQuestionService.getVocQuestionById(id);
		voc.put("question", question);
		if(question != null) {
			List<VocAnswerDto.Response> answer =  vocAnswerService.getVocAnswerByQuestionId(question.getId());
			if(answer != null) {
				voc.put("answer", answer);
			}
		}
		
		return new ResponseEntity<>(voc, HttpStatus.OK);
	}
	
	@PostMapping(value = "/answer")
	public ResponseEntity<VocAnswerDto.Response> saveVocAnswer(
			@RequestBody @Valid VocAnswerDto.Request vocAnswerRequest) {
		return new ResponseEntity<>(vocAnswerService.saveVocAnswer(vocAnswerRequest), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/questions")
	public ResponseEntity<List<VocQuestionDto.Response>> getLatestVocQuestions() {
		return new ResponseEntity<>(vocQuestionService.getLatestVocQuestions(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/answers")
	public ResponseEntity<List<VocAnswerDto.Response>> getLatestVocQAnswers() {
		return new ResponseEntity<>(vocAnswerService.getLatestVocQAnswers(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/categories")
	public ResponseEntity<List<VocCategoryDto.Response>> getCategories() {
		return new ResponseEntity<>(vocCategoryService.getVocCategory(), HttpStatus.OK);
	}
	
	
	@PatchMapping(value = "/question")
	public ResponseEntity<VocQuestionDto.Response> updateVocQuestion(
			Principal principal, 
			@RequestBody @Valid VocQuestionDto.UpdateRequest vocQuestionUpdateRequest) {
		String username = principal.getName();
		return new ResponseEntity<>(vocQuestionService.updateVocQuestion(username, vocQuestionUpdateRequest), HttpStatus.OK);
	}
	
	@PatchMapping(value = "/answer")
	public ResponseEntity<VocAnswerDto.Response> updateVocAnswer(
			@RequestBody @Valid VocAnswerDto.UpdateRequest vocAnswerUpdateRequest) {
		return new ResponseEntity<>(vocAnswerService.updateVocAnswer(vocAnswerUpdateRequest), HttpStatus.OK);
	}
	
}
