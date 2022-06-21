package com.toyseven.ymk.voc.question;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.VocQuestionDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.exception.BusinessException;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.model.entity.VocCategoryEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;
import com.toyseven.ymk.station.StationRepository;
import com.toyseven.ymk.voc.category.VocCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocQuestionServiceImpl implements VocQuestionService {
	private final VocQuestionRepository vocQuestionRepository;
	private final VocCategoryRepository vocCategoryRepository;
	private final StationRepository stationRepository;
	
	@Override
	public List<VocQuestionDto.Response> getAllVocQuestions() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findAll();
		return questions.stream().map(VocQuestionEntity::toVocQuestionResponse).collect(toList());
	}
	
	@Override
	public VocQuestionDto.Response saveVocQuestion(VocQuestionDto.Request vocQuestionRequest, String username) {
		VocCategoryEntity category = vocCategoryRepository.findById(vocQuestionRequest.getCategoryId())
				.orElseThrow(() -> new BusinessException("해당 Category 조회가 불가능 합니다.", ErrorCode.CATEGORY_IS_NOT_EXIST));
		StationInformationEntity station = stationRepository.findByStationId(vocQuestionRequest.getStationId());
		VocQuestionEntity question = vocQuestionRepository.save(vocQuestionRequest.toEntity(category, station, username));
				
		return question.toVocQuestionResponse();
	}
	
	@Override
	public VocQuestionDto.Response getVocQuestionById(Long id) {
		VocQuestionEntity question = vocQuestionRepository.findById(id)
				.orElseThrow(() -> new BusinessException("해당 Question 조회가 불가능 합니다.", ErrorCode.QUESTION_IS_NOT_EXIST));;
		
		return VocQuestionDto.Response.builder()
				.id(question.getId())
				.category(question.getCategory().getDisplayName())
				.title(question.getTitle())
				.content(question.getContent())
				.email(question.getEmail())
				.username(question.getUsername())
				.stationId(question.getStationId().getStationId())
				.needReply(question.getNeedReply())
				.createdAt(question.getCreatedAt())
				.updatedAt(question.getUpdatedAt())
				.build();
	}
	
	@Override
	public List<VocQuestionDto.Response> getLatestVocQuestions() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findTop10ByOrderByCreatedAtDesc();
		return questions.stream().map(VocQuestionEntity::toVocQuestionResponse).collect(toList());
	}

	@Override
	public VocQuestionDto.Response updateVocQuestion(String username, VocQuestionDto.UpdateRequest vocQuestionUpdateRequest) {
		
		VocQuestionEntity question = vocQuestionRepository.findById(vocQuestionUpdateRequest.getId())
				.orElseThrow(() -> new BusinessException("해당 Question 조회가 불가능 합니다.", ErrorCode.QUESTION_IS_NOT_EXIST));
				
		String compareUsername = question.getUsername();
		validQuestionWriter(username, compareUsername);
		question.update(vocQuestionUpdateRequest.getTitle(), vocQuestionUpdateRequest.getContent());
		
		return question.toVocQuestionResponse();
	}
	
	private void validQuestionWriter(final String username, final String compareUsername) {
        if (!username.equals(compareUsername)) {
        	throw new BusinessException("작성자만 수정 가능합니다.", ErrorCode.IS_NOT_WRITER);
        }
    }
	
	
}
