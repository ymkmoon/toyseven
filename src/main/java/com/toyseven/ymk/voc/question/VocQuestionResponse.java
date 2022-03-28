package com.toyseven.ymk.voc.question;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toyseven.ymk.common.model.dto.CustomLocalDateTimeDeserializer;
import com.toyseven.ymk.common.model.dto.CustomLocalDateTimeSerializer;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.model.entity.VocCategoryEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocQuestionResponse {
	private long id;
	@NotBlank private VocCategoryEntity category;
	@NotBlank private String title;
	@NotBlank private String content;
	@NotBlank private String email;
	@NotBlank private String username;
	@NotBlank private StationInformationEntity stationId;
	@NotNull private int needReply;
	
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
	private LocalDateTime createdAt;
	
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
	private LocalDateTime updatedAt;
}
