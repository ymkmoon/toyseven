package com.toyseven.ymk.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VocCategoryDto {
	
	@Getter
	public static class Response {
		private Long id;
		private String name;
		private String displayName;

		@Builder
		public Response(Long id, String name, String displayName) {
			this.id = id;
			this.name = name;
			this.displayName = displayName;
		}
	}
	
}
