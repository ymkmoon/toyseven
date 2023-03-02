package com.toyseven.ymk.station;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.util.ToysevenCommonUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StationJob {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final StationService stationService;
	
	@Scheduled(fixedDelay = 100000000)
    public void saveStations() {
        List<StationInformationEntity> stations = stationService.getStations();
        logger.info("==================== BEGIN Station Job ====================");
        if(!stations.isEmpty()) {
        	logger.info("Save data responsed from Third Party");
            stationService.saveStations(stations);
        } else {
        	logger.info("Save local data ");
        	stationService.saveStations(ToysevenCommonUtil.getStationsFromJsonArrayFile("StationFindAll.json"));
        }
        logger.info("==================== END Station Job ======================");
    }
}
