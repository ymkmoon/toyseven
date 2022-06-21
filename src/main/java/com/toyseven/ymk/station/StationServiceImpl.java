package com.toyseven.ymk.station;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.ResponseEntityUtil;
import com.toyseven.ymk.common.ToysevenCommonUtil;
import com.toyseven.ymk.common.WebClientUtil;
import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final ObjectMapper objectMapper;
    
    @Value("${api.key.station}") 
    private String SERVICE_KEY;
    private static final String DATA_TYPE = "JSON";
    private static final String SERVICE = "bikeList";
    
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";

    @Override
    public List<StationInformationDto.Response> getAllStations() {
    	List<StationInformationEntity> stations = stationRepository.findAll();
    	return stations.stream().map(StationInformationEntity::toStationInformationResponse).collect(toList());
    }

    @Override
    public List<StationInformationDto.Response> getStationByStationName(String stationName) {
    	List<StationInformationEntity> stations = stationRepository.findByStationNameContaining(stationName);
    	return stations.stream().map(StationInformationEntity::toStationInformationResponse).collect(toList());
    }
    
    @Override
	public List<StationInformationEntity> getStations() {
    	List<StationInformationEntity> row = requestToStation(1);
        return objectMapper.convertValue(row, new TypeReference<List<StationInformationEntity>>() {});
	}
    
    @Override
    public void save(List<StationInformationEntity> stations) {
    	stationRepository.saveAll(stations);
    }
    
    @SuppressWarnings("unchecked")
	private List<StationInformationEntity> requestToStation(int index) {
    	
    	WebClient wc = WebClientUtil.buildWebClient(BASE_URL, DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        List<StationInformationEntity> row = new ArrayList<>();
        
        StationInformationDto.Request stationRequest = setStationRequest(index);
        ResponseEntity<JSONObject> response = ResponseEntityUtil.stationApi(wc, stationRequest);
        if(!ToysevenCommonUtil.isSuccessResponse(response))
        	return row;

        Map<String, Object> responseData = (Map<String, Object>)response.getBody().get("getStationOpenApiJson");
        String listTotalCount = responseData != null ? responseData.entrySet().stream()
        		.filter(station -> station.getKey().equals("list_total_count"))
        		.map(station -> station.getValue().toString())
        		.findFirst()
        		.orElse("0") : "0";
        
        if(!listTotalCount.equals("0")) 
        	row.addAll((List<StationInformationEntity>)responseData.get("row"));
        
        if(index < 2001)
        	row.addAll(requestToStation(index+1000));
        return row;
    }
    
    private StationInformationDto.Request setStationRequest(int index) {
    	return StationInformationDto.Request
    			.builder()
    			.serviceKey(SERVICE_KEY)
    			.dataType(DATA_TYPE)
    			.service(SERVICE)
    			.startIndex(index)
    			.endIndex(index+999)
    			.build();
    	
    }
}
