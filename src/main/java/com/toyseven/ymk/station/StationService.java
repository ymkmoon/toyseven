package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

@Transactional(readOnly = true)
public interface StationService {
	List<StationInformationDto.Response> getAllStations(Pageable pageable);
	List<StationInformationDto.Response> getStationByStationName(String stationName);
	List<StationInformationEntity> getStations();
	@Transactional void saveStations(List<StationInformationEntity> stations);
}
