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
	public List<VocQuestionDto.Response> findAll() {
		List<VocQuestionEntity> questions = vocQuestionRepository.findAll();
		return questions.stream().map(VocQuestionEntity::toVocQuestionResponse).collect(toList());
	}
	
	@Override
	public VocQuestionDto.Response save(VocQuestionDto.Request vocQuestionRequest, String username) {
		VocCategoryEntity category = vocCategoryRepository.findById(vocQuestionRequest.getCategoryId())
				.orElseThrow(() -> new BusinessException("해당 Category 조회가 불가능 합니다.", ErrorCode.CATEGORY_IS_NOT_EXIST));
		StationInformationEntity station = stationRepository.findByStationId(vocQuestionRequest.getStationId())
				.orElseThrow(() -> new BusinessException("해당 Station 조회가 불가능 합니다.", ErrorCode.STATION_IS_NOT_EXIST));
		VocQuestionEntity question = vocQuestionRepository.save(vocQuestionRequest.toEntity(category, station, username));
				
		return question.toVocQuestionResponse();
	}
	
	@Override
	public VocQuestionDto.Response findVocQuestionById(Long id) {
		VocQuestionEntity question = vocQuestionRepository.findById(id).get();
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
	
}
