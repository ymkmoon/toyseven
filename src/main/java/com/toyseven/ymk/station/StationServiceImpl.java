package com.toyseven.ymk.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.ResponseEntityComponent;
import com.toyseven.ymk.common.WebClientFactory;
import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.search.StationSearchCondition;
import com.toyseven.ymk.common.util.DataParsingUtil;
import com.toyseven.ymk.common.util.ToysevenCommonUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StationServiceImpl implements StationService {

	private final ResponseEntityComponent responseEntityComponent;
    private final StationRepository stationRepository;
    private final ObjectMapper objectMapper;
    
    private final StationRepositoryCustom customRepository;
    
    @Value("${api.key.station}") 
    private String SERVICE_KEY;
    private static final String DATA_TYPE = "JSON";
    private static final String SERVICE = "bikeList";
    
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";

    @Override
    public List<StationInformationDto.Response> getAllStations(Pageable pageable) {
    	Page<StationInformationEntity> stations = stationRepository.findAll(pageable);
		return stations.map(StationInformationEntity::toStationInformationResponse).toList();
//    	return stations.stream().map(StationInformationEntity::toStationInformationResponse).collect(toList());
    }
    
    @Override
	public Page<StationInformationDto.Response> getStationsSearchable(Pageable pageable, StationSearchCondition condition) {
		return customRepository.searchStations(condition, pageable);
	}

    @Override
    public List<StationInformationDto.Response> getStationByStationName(String stationName, Pageable pageable) {
    	Page<StationInformationEntity> stations = stationRepository.findByStationNameContaining(stationName, pageable);
    	if(stations.isEmpty()) throw new NoSuchElementException(ErrorCode.NO_SUCH_ELEMENT.getDetail());
    	return stations.map(StationInformationEntity::toStationInformationResponse).toList();
//    	return stations.stream().map(StationInformationEntity::toStationInformationResponse).collect(toList());
    }
    
    @Override
	public List<StationInformationEntity> getStations() {
    	List<StationInformationEntity> row = requestToStation(1);
        return objectMapper.convertValue(row, new TypeReference<List<StationInformationEntity>>() {});
	}
    
    @Override
    public void saveStations(List<StationInformationEntity> stations) {
    	stationRepository.saveAll(stations);
    }
    
    @SuppressWarnings("unchecked")
	private List<StationInformationEntity> requestToStation(int index) {
    	
    	WebClient wc = new WebClientFactory(BASE_URL, DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY).buildWebClient();
        List<StationInformationEntity> row = new ArrayList<>();
        
        StationInformationDto.Request stationRequest = setStationRequest(index);
        ResponseEntity<JSONObject> response = responseEntityComponent.stationApi(wc, stationRequest);
        if(!ToysevenCommonUtil.isSuccessResponse(response))
        	return row;
        
        Map<String, Object> vehicleStatus = (Map<String, Object>)DataParsingUtil.toMap(response.getBody()).get("rentBikeStatus");

        String listTotalCount = ToysevenCommonUtil.isNotNullOrEmptyMap(vehicleStatus) ? 
        		vehicleStatus.entrySet().stream()
        		.filter(vehicle -> vehicle.getKey().equals("list_total_count"))
        		.map(vehicle -> vehicle.getValue().toString())
        		.findFirst().orElse("0") 
        		: "0";
        
        if(!listTotalCount.equals("0")) 
        	row.addAll((List<StationInformationEntity>)vehicleStatus.get("row"));
        
        if(index < 3001)
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
