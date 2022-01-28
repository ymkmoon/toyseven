package com.toyseven.ymk.station;

import java.util.ArrayList;
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
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StationService {

    private final StationRepository stationRepository;
    private final StationParam stationParam;

    public List<StationInformationEntity> findAll() {
        return stationRepository.findAll();
    }

    public Optional<List<StationInformationEntity>> findByStationName(String stationName) {
        return stationRepository.findByStationNameContaining(stationName);
    }
    
    @SuppressWarnings("unchecked")
	public List<StationInformationEntity> getStationList() {
		String BASE_URL = "http://openapi.seoul.go.kr:8088";
		
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();
        List<StationInformationEntity> row = new ArrayList<>();

        for(int i=1 ; i<=2001 ; i+=1000){
            stationParam.setStartIndex(i);
            stationParam.setEndIndex(i+999);

            ResponseEntity<JSONObject> response = wc.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/"+stationParam.getServiceKey())
                            .path("/"+stationParam.getDataType())
                            .path("/"+stationParam.getService())
                            .path("/"+stationParam.getStartIndex())
                            .path("/"+stationParam.getEndIndex())
                            .build()
                    ).accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(JSONObject.class)
                    .block();

            if(response != null && response.getStatusCode() == HttpStatus.OK){
            	Map<?, ?> responseBody = response.getBody() != null ? response.getBody() : null;
            	Map<String, Object> responseData = responseBody != null ? (Map<String, Object>)responseBody.get("rentBikeStatus") : null;
                
                if(responseData != null && !responseData.get("list_total_count").equals(0) && responseData.get("list_total_count") != null){
					List<StationInformationEntity> tmpRow = (List<StationInformationEntity>) responseData.get("row");
                    row.addAll(tmpRow);
                }
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(row, new TypeReference<List<StationInformationEntity>>() {});
	}
    
    public void save(List<StationInformationEntity> stations) {
    	stationRepository.saveAll(stations);
    }
}
