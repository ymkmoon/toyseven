package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.util.ToysevenCommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StationJob {
	
	private final StationService stationService;
	
	@Scheduled(fixedDelay = 100000000)
    public void saveStations() {
        List<StationInformationEntity> stations = stationService.getStations();
        log.info("==================== BEGIN Station Job ====================");
        if(!stations.isEmpty()) {
        	log.info("Save data responsed from Third Party");
            stationService.saveStations(stations);
        } else {
        	log.info("Save local data ");
        	stationService.saveStations(ToysevenCommonUtil.getStationsFromJsonArrayFile("StationFindAll.json"));
        }
        log.info("==================== END Station Job ======================");
    }
}
