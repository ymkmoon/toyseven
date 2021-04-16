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
	private String name;
	@Column(name = "latitude", nullable = false, updatable = true, insertable = true)
	private Double latitude;
	@Column(name = "longitude", nullable = false, updatable = true, insertable = true)
	private Double longitude;
	@Column(name = "total_rack_count", nullable = false, updatable = true, insertable = true)
	private String totalRackCount;
	@Column(name = "total_parking_bike_count", nullable = false, updatable = true, insertable = true)
	private String totalParkingBikeCount;
	
	public StationInformation() {}

	@Builder
	public StationInformation(String stationId, String name, Double latitude, Double longitude,
			String totalRackCount, String totalParkingBikeCount) {
		this.stationId = stationId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.totalRackCount = totalRackCount;
		this.totalParkingBikeCount = totalParkingBikeCount;
	}
}
