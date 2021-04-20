package com.toyseven.ymk.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ROLE_USER("ROLE_USER", "일반 사용자"), ROLE_ADMIN("ROLE_ADMIN", "관리자");
	
	private final String key;
	private final String title;
	public String getKey() {
		return key;
	}
	public String getTitle() {
		return title;
	}
}
