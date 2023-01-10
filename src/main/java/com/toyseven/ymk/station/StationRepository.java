package com.toyseven.ymk.station;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.common.model.entity.StationInformationEntity;

@Repository
public interface StationRepository extends JpaRepository<StationInformationEntity, Long> {
	Page<StationInformationEntity> findByStationNameContaining(String name, Pageable pageable);
	StationInformationEntity findByStationId(String stationId);
}
