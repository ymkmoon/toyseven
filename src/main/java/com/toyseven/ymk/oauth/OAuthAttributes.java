package com.toyseven.ymk.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	
	@Autowired User user;
	
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
			Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder().name((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.attributes(attributes)
//				.picture((String) attributes.get("picture")).attributes(attributes)
				.nameAttributeKey(userNameAttributeName).build();
	}

	public User toEntity() {
		return User.builder().name(name).email(email).role(Role.ROLE_USER).build();
	}
}
