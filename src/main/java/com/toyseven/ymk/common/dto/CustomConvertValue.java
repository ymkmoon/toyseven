package com.toyseven.ymk.common.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.dto.voc.VocAnswerResponse;
import com.toyseven.ymk.common.model.entity.VocAnswerEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomConvertValue {
	private final ObjectMapper objectMapper;
	
	public List<VocAnswerResponse> vocAnswerEntityToDto(List<VocAnswerEntity> list) {
		return objectMapper.convertValue(list, new TypeReference<List<VocAnswerResponse>>() {});
	}
	
	
}
