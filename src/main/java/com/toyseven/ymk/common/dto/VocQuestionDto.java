package com.toyseven.ymk.common.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.model.entity.VocCategoryEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
		@NotNull private boolean active;
		
		public VocQuestionEntity toEntity(VocCategoryEntity category, StationInformationEntity station, String username, boolean active) {
	        return VocQuestionEntity.builder()
	        		.category(category)
	        		.title(title)
	        		.content(content)
	                .email(email)
	                .username(username)
	                .stationId(station)
	                .needReply(needReply)
	                .active(active)
	                .build();
	    }
	}
	
	@Getter
	@NoArgsConstructor
	public static class UpdateRequest {
		
		@NotNull private Long id;
		@NotBlank private String title;
		@NotBlank private String content;
	}
	
	@Getter
	public static class Response {
		private Long id;
		private String category;
		private String title;
		private String content;
		private String email;
		private String username;
		private String stationId;
		private int needReply;
		private boolean active;
		
		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
		private LocalDateTime createdAt;
		
		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
		private LocalDateTime updatedAt;

		@Builder
		public Response(Long id, String category, String title, String content, String email, String username,
				String stationId, int needReply, LocalDateTime createdAt, LocalDateTime updatedAt, boolean active) {
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
			this.active = active;
		}
	}

}
