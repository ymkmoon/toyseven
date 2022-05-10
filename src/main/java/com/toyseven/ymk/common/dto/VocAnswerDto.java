package com.toyseven.ymk.common.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.VocAnswerEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VocAnswerDto {
	@Getter
	@NoArgsConstructor
	public static class Request {
		private Long id;
		@NotNull private Long questionId;
		@NotBlank private String content;
		
		public VocAnswerEntity toEntity(VocQuestionEntity question, AdminEntity admin) {
			return VocAnswerEntity.builder()
					.questionId(question)
					.content(content)
					.adminId(admin)
					.build();
		}
	}
	
	@Builder
	@Getter
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private long questionId;
		private String content;
		private String adminName;

		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
		private LocalDateTime createdAt;
		
		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
		private LocalDateTime updatedAt;
	}

}
