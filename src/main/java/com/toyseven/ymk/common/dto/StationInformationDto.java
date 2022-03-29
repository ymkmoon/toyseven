package com.toyseven.ymk.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Request {
		private String serviceKey;
	    private String dataType;
	    private String service;
		private Integer startIndex;
		private Integer endIndex;
	}
}
