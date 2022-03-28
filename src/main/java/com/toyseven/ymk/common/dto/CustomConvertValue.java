package com.toyseven.ymk.common.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.dto.station.StationInformationResponse;
import com.toyseven.ymk.common.dto.voc.VocAnswerResponse;
import com.toyseven.ymk.common.dto.voc.VocQuestionResponse;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.model.entity.VocAnswerEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomConvertValue {
	private final ObjectMapper objectMapper;
	
	public List<VocAnswerResponse> vocAnswerEntityToDto(List<VocAnswerEntity> list) {
		return objectMapper.convertValue(list, new TypeReference<List<VocAnswerResponse>>() {});
	}
	
	public VocQuestionResponse vocQuestionEntityToDto(VocQuestionEntity question) {
		return objectMapper.convertValue(question, new TypeReference<VocQuestionResponse>() {});
	}
	
	public List<VocQuestionResponse> vocQuestionEntityToDto(List<VocQuestionEntity> list) {
		return objectMapper.convertValue(list, new TypeReference<List<VocQuestionResponse>>() {});
	}
	
	public List<StationInformationResponse> stationEntityToDto(List<StationInformationEntity> list) {
		return objectMapper.convertValue(list, new TypeReference<List<StationInformationResponse>>() {});
	}
	
	public List<StationInformationEntity> stationDtoToEntity(List<StationInformationResponse> list) {
		return objectMapper.convertValue(list, new TypeReference<List<StationInformationEntity>>() {});
	}
	
	
}
