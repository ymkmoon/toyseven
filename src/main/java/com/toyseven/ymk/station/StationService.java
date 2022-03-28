package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.station.StationInformationResponse;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

@Transactional
public interface StationService {
	List<StationInformationResponse> findAll();
	List<StationInformationResponse> findByStationName(String stationName);
	List<StationInformationEntity> getStations();
	void save(List<StationInformationEntity> stations);
}
