package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.search.StationSearchCondition;

@Transactional(readOnly = true)
public interface StationService {
	List<StationInformationDto.Response> getAllStations(Pageable pageable);
	Page<StationInformationDto.Response> getStationsSearchable(Pageable pageable, StationSearchCondition condition);
	
	List<StationInformationDto.Response> getStationByStationName(String stationName, Pageable pageable);
	List<StationInformationEntity> getStations();
	@Transactional void saveStations(List<StationInformationEntity> stations);
}
