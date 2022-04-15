package com.toyseven.ymk.station;

import java.util.ArrayList;
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
	
	@Scheduled(fixedDelay = 1000000)
    public void saveStations() {
        List<StationInformationEntity> stations = stationService.getStations();
        stations = new ArrayList<>();
        if(!stations.isEmpty()) {
            stationService.save(stations);
        } else {
        	stationService.save(ToysevenCommonUtil.getStationsFromJsonArrayFile("StationFindAll.json"));
        }
    }
}
