package com.toyseven.ymk.apiTest;

import java.net.URI;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ReturnResponseEntity {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<String> getResponseString(String apiUrl, HttpEntity<?> entity) {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        ResponseEntity<String> response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.GET, entity, String.class);
        sendMessage(response);
        return response;
	}
	
	public ResponseEntity<String> postResponseString(String apiUrl, HttpEntity<?> entity) {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
		ResponseEntity<String> response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.POST, entity, String.class);
		sendMessage(response);
		return response;
	}
	
	public ResponseEntity<JSONObject> getResponseJsonObject(String apiUrl, HttpEntity<?> entity) {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
		ResponseEntity<JSONObject> response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.GET, entity, JSONObject.class);
		sendMessage(response);
		return response;
	}
	
	public ResponseEntity<JSONObject> postResponseJsonObject(String apiUrl, HttpEntity<?> entity) {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
		ResponseEntity<JSONObject> response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.POST, entity, JSONObject.class);
		sendMessage(response);
		return response;
	}
	
	private void sendMessage(ResponseEntity<?> response) {
		logger.debug("## sendMessage : {}",  response.getStatusCode());
        logger.debug("## sendMessage : {}",  response.getBody());
	}
	
	public void getMethodName() {
		logger.info(Thread.currentThread().getStackTrace()[2].getClassName()+" : "+Thread.currentThread().getStackTrace()[2].getMethodName());
	}
}
