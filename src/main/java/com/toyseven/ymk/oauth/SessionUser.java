package com.toyseven.ymk.oauth;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -578857586820574033L;
	private String name;
	private String email;

	public SessionUser(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
	}
}
