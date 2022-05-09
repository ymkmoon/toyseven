package com.toyseven.ymk.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class VocCategoryDto {
	
	@Builder
	@Getter
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private String name;
		private String displayName;
	}
	
}
