package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.toyseven.ymk.common.ToysevenCommonUtil;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StationJob {
	
	private final StationService stationService;
	
	@Scheduled(fixedDelay = 100000000)
    public void saveStations() {
        List<StationInformationEntity> stations = stationService.getStations();
        if(!stations.isEmpty()) {
            stationService.saveStations(stations);
        } else {
        	stationService.saveStations(ToysevenCommonUtil.getStationsFromJsonArrayFile("StationFindAll.json"));
        }
    }
}
