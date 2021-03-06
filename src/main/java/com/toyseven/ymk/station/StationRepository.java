package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.common.model.entity.StationInformationEntity;

@Repository
public interface StationRepository extends JpaRepository<StationInformationEntity, Long> {
	List<StationInformationEntity> findByStationNameContaining(String name);
	StationInformationEntity findByStationId(String stationId);
}
