package com.toyseven.ymk.common.dto;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AdminDto {
	@Getter
	public static class Request {
	    @NotNull
	    private String username;
	    @NotNull
	    private String password;
	}

	@Builder
	@Getter
	@AllArgsConstructor
	public static class Response {
	    @NotNull
	    private String accessToken;
	}

}
