package com.toyseven.ymk.common.dto;


import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminDto {
	
	@Getter
	@NoArgsConstructor
	public static class Request {
	    @NotBlank private String username;
	    @NotBlank private String password;

	    @Builder
		public Request(@NotBlank String username, @NotBlank String password) {
			this.username = username;
			this.password = password;
		}
	}

}
