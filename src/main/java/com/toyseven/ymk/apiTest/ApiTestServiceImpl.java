package com.toyseven.ymk.apiTest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiTestServiceImpl implements ApiTestService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<?> apiTest() {
		return callApi();
	}
	
	private List<?> callApi() {
		List<?> list = null;
		
		StringBuffer strBufUrl = new StringBuffer();
		strBufUrl.append("http://openapi.seoul.go.kr:8088");
		strBufUrl.append("/784e68756e73696c36334f5a426b4a");
		strBufUrl.append("/json");
		strBufUrl.append("/bikeList");
		strBufUrl.append("/1/10");
		
		String apiUrl = strBufUrl.toString();
		
		HttpHeaders headers = new HttpHeaders();
//				headers.add("", );
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
		ResponseEntity<JSONObject> response = restTemplate.exchange(URI.create(apiUrl), HttpMethod.GET, entity, JSONObject.class);
		
		logger.debug("## sendMessage : {}",  response.getStatusCode());
        logger.debug("## sendMessage : {}",  response.getBody());
        
        if(response.getStatusCodeValue() == 200) {
        	if(response.hasBody()) {
        		logger.info("## sendMessage : {}",  response.getBody().get("rentBikeStatus"));
        		Map<?, ?> map = (Map<?, ?>) response.getBody().get("rentBikeStatus");
        		list = (List<?>) map.get("row");
        	}
        }
        
		return list;
	}
	
}
