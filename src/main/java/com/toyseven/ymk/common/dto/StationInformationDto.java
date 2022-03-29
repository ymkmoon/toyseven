package com.toyseven.ymk.common.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
	@Component
	public static class Request {
		@Value("${api.key.station}")
	    private String serviceKey;
		private final String dataType = "JSON";
		private final String service = "bikeList";
		private Integer startIndex;
		private Integer endIndex;
	}
}
