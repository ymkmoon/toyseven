package com.toyseven.ymk.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constants {
	
	ACCESS_TOKEN("AccessToken"),
	REFRESH_TOKEN("RefreshToken"),
	USERNAME("username"),
	EMPTY("Empty")
	;
	
	private final String title;
	
}
