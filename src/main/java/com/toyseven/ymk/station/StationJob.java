package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.toyseven.ymk.common.dto.CustomConvertValue;
import com.toyseven.ymk.common.dto.station.StationInformationResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StationJob {
	
	private final StationService stationService;
	private final CustomConvertValue customConvertValue;
	
	@Scheduled(fixedDelay = 100000)
    public void saveStations() {
        List<StationInformationResponse> stations = stationService.getStations();
        
        if(stations != null) {
            stationService.save(customConvertValue.stationDtoToEntity(stations));
        }
    }
}
