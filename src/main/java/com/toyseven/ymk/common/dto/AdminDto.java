package com.toyseven.ymk.common.dto;


import javax.validation.constraints.NotNull;

import lombok.Getter;

public class AdminDto {
	@Getter
	public static class Request {
	    @NotNull
	    private String username;
	    @NotNull
	    private String password;
	}

}
