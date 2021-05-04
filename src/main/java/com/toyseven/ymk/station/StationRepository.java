package com.toyseven.ymk.station;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.model.StationInformation;

@Repository
public interface StationRepository extends JpaRepository<StationInformation, Long> {
	Optional<List<StationInformation>> findByStationName(String stationName);
}
