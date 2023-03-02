package com.toyseven.ymk.common;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebClientFactory {
	
	private final String baseUrl;
	private final EncodingMode encodingMode;
	
	public WebClient buildWebClient() {
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(encodingMode);
        return WebClient.builder().uriBuilderFactory(factory).baseUrl(baseUrl).build();
	}
	
}
