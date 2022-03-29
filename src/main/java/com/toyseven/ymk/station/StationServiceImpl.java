package com.toyseven.ymk.station;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.ToysevenCommonUtil;
import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final StationInformationDto.Request stationParam;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";

    @Override
    public List<StationInformationDto.Response> findAll() {
    	List<StationInformationEntity> stations = stationRepository.findAll();
    	return stations.stream().map(StationInformationEntity::toStationInformationResponse).collect(toList());
    }

    @Override
    public List<StationInformationDto.Response> findByStationName(String stationName) {
    	List<StationInformationEntity> stations = stationRepository.findByStationNameContaining(stationName).get();
    	return stations.stream().map(StationInformationEntity::toStationInformationResponse).collect(toList());
    }
    
    @Override
	public List<StationInformationEntity> getStations() {
    	List<StationInformationEntity> row = reuqestToStation(1);
        return objectMapper.convertValue(row, new TypeReference<List<StationInformationEntity>>() {});
	}
    
    @Override
    public void save(List<StationInformationEntity> stations) {
    	stationRepository.saveAll(stations);
    }
    
    @SuppressWarnings("unchecked")
	private List<StationInformationEntity> reuqestToStation(int index) {
    	DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();
        List<StationInformationEntity> row = new ArrayList<>();
        
        stationParam.setStartIndex(index);
        stationParam.setEndIndex(index+999);

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
        
        if(!ToysevenCommonUtil.isSuccessResponse(response))
        	return row;

        Map<String, Object> responseData = (Map<String, Object>)response.getBody().get("rentBikeStatus");
        String listTotalCount = responseData.entrySet().stream()
        		.filter(station -> station.getKey().equals("list_total_count"))
        		.map(station -> station.getValue().toString())
        		.findFirst()
        		.orElse("0");
        
        if(!listTotalCount.equals("0")) 
        	row.addAll((List<StationInformationEntity>)responseData.get("row"));
        
        if(index < 2001)
        	row.addAll(reuqestToStation(index+1000));
        return row;
    }
}
