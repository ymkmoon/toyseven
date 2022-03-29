package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.station.StationInformationDto;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

@Transactional
public interface StationService {
	List<StationInformationDto.Response> findAll();
	List<StationInformationDto.Response> findByStationName(String stationName);
	List<StationInformationEntity> getStations();
	void save(List<StationInformationEntity> stations);
}
