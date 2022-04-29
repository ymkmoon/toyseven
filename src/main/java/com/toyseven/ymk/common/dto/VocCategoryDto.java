package com.toyseven.ymk.common.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class VocCategoryDto {
	
	@Builder
	@Getter
	@AllArgsConstructor
	public static class Response {
		private Long id;
		@NotBlank private String name;
		@NotBlank private String displayName;
		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
		private LocalDateTime createdAt;
		
		@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class) 
		@JsonSerialize(using = CustomLocalDateTimeSerializer.class) 
		private LocalDateTime updatedAt;
	}
	
}
