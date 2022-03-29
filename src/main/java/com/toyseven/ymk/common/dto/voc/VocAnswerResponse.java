package com.toyseven.ymk.common.dto.voc;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toyseven.ymk.common.dto.CustomLocalDateTimeDeserializer;
import com.toyseven.ymk.common.dto.CustomLocalDateTimeSerializer;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VocAnswerResponse {
	private long id;
	@NotBlank private long questionId;
	@NotBlank private String content;
	@NotBlank private String admin;

	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime createdAt;
	
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime updatedAt;
	
	@Builder
	public VocAnswerResponse(long id, long questionId, String content, String admin, 
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.questionId = questionId;
		this.content = content;
		this.admin = admin;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
