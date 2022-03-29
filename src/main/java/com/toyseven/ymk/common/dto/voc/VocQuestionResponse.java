package com.toyseven.ymk.common.dto.voc;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toyseven.ymk.common.dto.CustomLocalDateTimeDeserializer;
import com.toyseven.ymk.common.dto.CustomLocalDateTimeSerializer;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VocQuestionResponse {
	private long id;
	@NotBlank private String category;
	@NotBlank private String title;
	@NotBlank private String content;
	@NotBlank private String email;
	@NotBlank private String username;
	@NotBlank private String stationId;
	@NotNull private int needReply;
	
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
	private LocalDateTime createdAt;
	
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
	private LocalDateTime updatedAt;
	
	@Builder
	public VocQuestionResponse(long id, String category, String title, String content,
			String email, String username, String stationId, int needReply,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.category = category;
		this.title = title;
		this.content = content;
		this.email = email;
		this.username = username;
		this.stationId = stationId;
		this.needReply = needReply;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
