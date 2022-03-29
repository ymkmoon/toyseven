package com.toyseven.ymk.common.dto.station;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class StationInformationDto {

	@Builder
	@Getter
	@AllArgsConstructor
	public static class Response {
		private String stationId;
	    private String stationName;
	    private Double stationLatitude;
	    private Double stationLongitude;
	    private Integer rackTotCnt;
	    private Integer parkingBikeTotCnt;
	    private Integer shared;
	}
}
