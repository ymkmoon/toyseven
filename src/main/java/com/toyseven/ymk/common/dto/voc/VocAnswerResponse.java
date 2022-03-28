package com.toyseven.ymk.common.dto.voc;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toyseven.ymk.common.dto.CustomLocalDateTimeDeserializer;
import com.toyseven.ymk.common.dto.CustomLocalDateTimeSerializer;
import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocAnswerResponse {
	private long id;
	@NotBlank private VocQuestionEntity questionId;
	@NotBlank private String content;
	@NotBlank private AdminEntity adminId;

	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime createdAt;
	
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime updatedAt;
}
