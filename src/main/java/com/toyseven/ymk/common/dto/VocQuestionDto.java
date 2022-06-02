package com.toyseven.ymk.common.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.model.entity.VocCategoryEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VocQuestionDto {
	
	@Getter
	@NoArgsConstructor
	public static class Request {
		@NotNull private Long categoryId;
		@NotBlank private String title;
		@NotBlank private String content;
		@NotBlank private String email;
		@NotNull private String stationId;
		@NotNull private int needReply;
		
		public VocQuestionEntity toEntity(VocCategoryEntity category, StationInformationEntity station, String username) {
	        return VocQuestionEntity.builder()
	        		.category(category)
	        		.title(title)
	        		.content(content)
	                .email(email)
	                .username(username)
	                .stationId(station)
	                .needReply(needReply)
	                .build();
	    }
	}
	
	@Builder
	@Getter
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private String category;
		private String title;
		private String content;
		private String email;
		private String username;
		private String stationId;
		private int needReply;
		
		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
		private LocalDateTime createdAt;
		
		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
		private LocalDateTime updatedAt;
	}

}
