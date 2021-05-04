package com.toyseven.ymk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name="station_information")
public class StationInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = true)
	private long id;
	@Column(name = "station_id", nullable = false, updatable = true, insertable = true)
	private String stationId;
	@Column(name = "name", nullable = false, updatable = true, insertable = true)
	private String stationName;
	@Column(name = "latitude", nullable = false, updatable = true, insertable = true)
	private Double stationLatitude;
	@Column(name = "longitude", nullable = false, updatable = true, insertable = true)
	private Double stationLongitude;
	@Column(name = "total_rack_count", nullable = false, updatable = true, insertable = true)
	private String rackTotCnt;
	@Column(name = "total_parking_bike_count", nullable = false, updatable = true, insertable = true)
	private String parkingBikeTotCnt;
	
	@Column(name = "shared", nullable = true, updatable = true, insertable = true)
	private int shared;
	
	public StationInformation() {}

	@Builder
	public StationInformation(String stationId, String stationName, Double stationLatitude, Double stationLongitude,
			String rackTotCnt, String parkingBikeTotCnt, int shared) {
		this.stationId = stationId;
		this.stationName = stationName;
		this.stationLatitude = stationLatitude;
		this.stationLongitude = stationLongitude;
		this.rackTotCnt = rackTotCnt;
		this.parkingBikeTotCnt = parkingBikeTotCnt;
		this.shared = shared;
	}
}
