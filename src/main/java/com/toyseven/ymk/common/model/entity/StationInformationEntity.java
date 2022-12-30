package com.toyseven.ymk.common.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.toyseven.ymk.common.dto.StationInformationDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="station_information")
public class StationInformationEntity {
    @Id
    @Column(name = "station_id", unique = true, nullable = false, updatable = false)
    private String stationId;

    @Column(name = "name", nullable = false)
    private String stationName;

    @Column(name = "latitude", nullable = false)
    private Double stationLatitude;

    @Column(name = "longitude", nullable = false)
    private Double stationLongitude;

    @Column(name = "total_rack_count", nullable = false)
    private Integer rackTotCnt;

    @Column(name = "total_parking_bike_count", nullable = false)
    private Integer parkingBikeTotCnt;

    @Column(name = "shared")
    private Integer shared;
    
    public StationInformationEntity(String stationId) {
    	this.stationId = stationId;
    }

    @Builder
    public StationInformationEntity(String stationName, Double stationLatitude, Double stationLongitude,
                              Integer rackTotCnt, Integer parkingBikeTotCnt, Integer shared) {
        this.stationName = stationName;
        this.stationLatitude = stationLatitude;
        this.stationLongitude = stationLongitude;
        this.rackTotCnt = rackTotCnt;
        this.parkingBikeTotCnt = parkingBikeTotCnt;
        this.shared = shared;
    }
    
    public StationInformationDto.Response toStationInformationResponse() {
    	return StationInformationDto.Response.builder()
    			.stationId(stationId)
    			.stationName(stationName)
    			.stationLatitude(stationLatitude)
    			.stationLongitude(stationLongitude)
    			.rackTotCnt(rackTotCnt)
    			.parkingBikeTotCnt(parkingBikeTotCnt)
    			.shared(shared)
    			.build();
    }
}
