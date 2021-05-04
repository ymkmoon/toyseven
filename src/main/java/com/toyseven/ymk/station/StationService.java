package com.toyseven.ymk.station;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.model.StationInformation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StationService {
	private final StationRepository stationRepository;

    public List<StationInformation> findAll() {
        return stationRepository.findAll();
    }

    public Optional<List<StationInformation>> findByStationName(String stationName) {
        return stationRepository.findByStationNameContaining(stationName);
    }
    
    public List<StationInformation> getStationList() {
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
                		.path("/1000")
                        .build()
                ).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(JSONObject.class)
                .block();
		
        if(response.getStatusCode() == HttpStatus.OK ){
            Map<?, ?> responseData = (Map<?, ?>) response.getBody().get("rentBikeStatus");
            if(!responseData.get("list_total_count").equals(0) && responseData.get("list_total_count") != null) {
            	List<?> row = (List<?>) responseData.get("row");
            	ObjectMapper mapper = new ObjectMapper();
         	   	List<StationInformation> stations =  mapper.convertValue(row, new TypeReference<List<StationInformation>>() {});
         	   	return stations;
            }
        }
        return null;
	}
    
    public void save(List<StationInformation> stations) {
    	stationRepository.saveAll(stations);
    }

}
