package com.toyseven.ymk.common.dto;


import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AdminDto {
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
	    @NotNull
	    private String username;
	    @NotNull
	    private String password;
	}

}
