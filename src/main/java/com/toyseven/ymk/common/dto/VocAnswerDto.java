package com.toyseven.ymk.common.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.VocAnswerEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VocAnswerDto {
	
	@Getter
	@NoArgsConstructor
	public static class Request {
		@NotNull private Long questionId;
		@NotBlank private String content;
		@NotNull private boolean isActive;
		
		public VocAnswerEntity toEntity(VocQuestionEntity question, AdminEntity admin, boolean isActive) {
			return VocAnswerEntity.builder()
					.questionId(question)
					.content(content)
					.adminId(admin)
					.isActive(isActive)
					.build();
		}
	}
	
	@Getter
	@NoArgsConstructor
	public static class UpdateRequest {
		@NotNull private Long id;
		@NotBlank private String content;
	}
	
	@Getter
	public static class Response {
		private Long id;
		private long questionId;
		private String content;
		private String adminName;
		private boolean isActive;

		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
		private LocalDateTime createdAt;
		
		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
		private LocalDateTime updatedAt;

		@Builder
		public Response(Long id, long questionId, String content, String adminName, LocalDateTime createdAt,
				LocalDateTime updatedAt, boolean isActive) {
			this.id = id;
			this.questionId = questionId;
			this.content = content;
			this.adminName = adminName;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.isActive = isActive;
		}
	}

}
