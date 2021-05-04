package com.toyseven.ymk.station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.apiTest.ReturnResponseEntity;
import com.toyseven.ymk.model.StationInformation;

import lombok.RequiredArgsConstructor;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
@Service
public class StationService {
	private final StationRepository stationRepository;

    public List<StationInformation> findAll() {
        return stationRepository.findAll();
    }

    public Optional<List<StationInformation>> findByStationName(String stationName) {
        return stationRepository.findByStationName(stationName);
    }
    
//    public List<StationInformation> save(StationInformation list) {
//		return stationRepository.saveAll(list);
//	}
	
//	public List<StationInformation> callApi() {
//		List<StationInformation> list = null;
//		
//		StringBuffer strBufUrl = new StringBuffer();
//		strBufUrl.append("http://openapi.seoul.go.kr:8088");
//		strBufUrl.append("/784e68756e73696c36334f5a426b4a");
//		strBufUrl.append("/json");
//		strBufUrl.append("/bikeList");
//		strBufUrl.append("/1/10");
//		
//		String apiUrl = strBufUrl.toString();
//		
//		HttpHeaders headers = new HttpHeaders();
////				headers.add("", );
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		
//		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
//		
//		new ReturnResponseEntity().getMethodName();
//		ResponseEntity<JSONObject> response = new ReturnResponseEntity().getResponseJsonObject(apiUrl, entity);
//		
//        if(response.getStatusCodeValue() == 200) {
//        	if(response.hasBody()) {
//        		Map<?, ?> map = (Map<?, ?>) response.getBody().get("rentBikeStatus");
//        		if(!String.valueOf(map.get("list_total_count")).equals("0")) {
//	        		list = (List<StationInformation>) map.get("row");
//	        		System.out.println("map.get(\"row\").getClass() : "+map.get("row").getClass());
//        		}
//        	}
//        }
////        
////        List<Map> list2 = new ArrayList();
////        for(int index=0; index<list.size(); index++) {
////        	System.out.println("list.get(0).getClass() : "+list.get(index).getClass());
////        	Map map = (Map) list.get(index);
////        	map.put("id", index+1);
////        	list2.add(map);
////        }
////        System.out.println("zero index : "+list.get(0));
////        stationRepository.saveAll(list2);
////        Iterable<StationInformation> iter = (Iterable<StationInformation>) list.iterator();
////        stationRepository.saveAll(iter);
//        
//        System.out.println("list size : "+list.size());
//		return list;
//	}
	
	
	public void callApi() {
		String BASE_URL = "http://openapi.seoul.go.kr:8088";
		
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();
        
        ResponseEntity<JSONObject> response = wc.get()
                .uri(uriBuilder -> uriBuilder
                		.path("/784e68756e73696c36334f5a426b4a")
                		.path("/json")
                		.path("/bikeList")
                		.path("/1")
                		.path("/10")
                        .build()
                ).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(JSONObject.class)
                .block();
		
        if(response.getStatusCode() == HttpStatus.OK ){
            Map<?, ?> responseData = (Map<?, ?>) response.getBody().get("rentBikeStatus");
            if(!responseData.get("list_total_count").equals(0) && responseData.get("list_total_count") != null) {
            	List<?> row = (List<?>) responseData.get("row");
            	System.out.println("row : "+row);
            	
            	ObjectMapper mapper = new ObjectMapper();
         	   	List<StationInformation> list =  mapper.convertValue(row, new TypeReference<List<StationInformation>>() {});
         	   stationRepository.saveAll(list);
            }
        }
	}


}
