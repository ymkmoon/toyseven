package com.toyseven.ymk.station;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.model.entity.StationInformationEntity;

@Transactional
public interface StationService {
	List<StationInformationEntity> findAll();
	Optional<List<StationInformationEntity>> findByStationName(String stationName);
	List<StationInformationEntity> getStationList();
	void save(List<StationInformationEntity> stations);
}
