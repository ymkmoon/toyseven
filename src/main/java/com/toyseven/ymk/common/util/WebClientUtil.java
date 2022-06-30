package com.toyseven.ymk.common.util;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebClientUtil {
	
	public static WebClient buildWebClient(String baseUrl, EncodingMode encodingMode) {
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(encodingMode);
        return WebClient.builder().uriBuilderFactory(factory).baseUrl(baseUrl).build();
	}
	
}
