package com.toyseven.ymk.voc.question;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.VocQuestionDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.exception.BusinessException;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.model.entity.VocCategoryEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;
import com.toyseven.ymk.common.search.VocQuestionSearchCondition;
import com.toyseven.ymk.station.StationRepository;
import com.toyseven.ymk.voc.category.VocCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocQuestionServiceImpl implements VocQuestionService {
	
	private final VocQuestionRepository vocQuestionRepository;
	private final VocCategoryRepository vocCategoryRepository;
	private final StationRepository stationRepository;
	
	private final VocQuestionRepositoryCustom customRepository;
	
	@Override
	public List<VocQuestionDto.Response> getAllVocQuestions(Pageable pageable) {
		Page<VocQuestionEntity> questions = vocQuestionRepository.findAll(pageable);
		return questions.map(VocQuestionEntity::toVocQuestionResponse).toList();
//		return questions.stream().map(VocQuestionEntity::toVocQuestionResponse).collect(toList());
	}
	
	@Override
	public Page<VocQuestionDto.Response> getVocQuestionsSearchable(Pageable pageable, VocQuestionSearchCondition condition) {
		return customRepository.searchVocQuestion(condition, pageable);
	}
	
	@Override
	public VocQuestionDto.Response saveVocQuestion(VocQuestionDto.Request vocQuestionRequest, String username) {
		VocCategoryEntity category = vocCategoryRepository.findById(vocQuestionRequest.getCategoryId())
				.orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_IS_NOT_EXIST));
		StationInformationEntity station = stationRepository.findByStationId(vocQuestionRequest.getStationId());
		
		VocQuestionEntity question = vocQuestionRepository.save(vocQuestionRequest.toEntity(category, station, username, true));
				
		return question.toVocQuestionResponse();
	}
	
	@Override
	public VocQuestionDto.Response getVocQuestionById(Long id) {
		VocQuestionEntity question = vocQuestionRepository.findById(id)
				.orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_IS_NOT_EXIST));
		return question.toVocQuestionResponse();
	}
	
	@Override
	public List<VocQuestionDto.Response> getLatestVocQuestions() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findTop10ByOrderByCreatedAtDesc();
		return questions.stream().map(VocQuestionEntity::toVocQuestionResponse).collect(toList());
	}

	@Override
	public VocQuestionDto.Response updateVocQuestion(String username, VocQuestionDto.UpdateRequest vocQuestionUpdateRequest) {
		
		VocQuestionEntity question = vocQuestionRepository.findById(vocQuestionUpdateRequest.getId())
				.orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_IS_NOT_EXIST));
				
		String compareUsername = question.getUsername();
		validQuestionWriter(username, compareUsername);
		question.update(vocQuestionUpdateRequest.getTitle(), vocQuestionUpdateRequest.getContent(), vocQuestionUpdateRequest.isActive());
		
		return question.toVocQuestionResponse();
	}
	
	private void validQuestionWriter(final String username, final String compareUsername) {
        if (!username.equals(compareUsername)) {
        	throw new BusinessException(ErrorCode.IS_NOT_WRITER);
        }
    }
	
	
}
