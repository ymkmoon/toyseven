package com.toyseven.ymk.station;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<StationInformationEntity, Long> {
	Optional<List<StationInformationEntity>> findByStationNameContaining(String name);
}
