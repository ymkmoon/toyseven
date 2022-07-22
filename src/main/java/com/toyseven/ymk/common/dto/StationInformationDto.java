package com.toyseven.ymk.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationInformationDto {
	
	@Getter
	@NoArgsConstructor
	public static class Request {
		private String serviceKey;
		private String dataType;
		private String service;
		private Integer startIndex;
		private Integer endIndex;

		@Builder
		public Request(String serviceKey, String dataType, String service, Integer startIndex, Integer endIndex) {
			this.serviceKey = serviceKey;
			this.dataType = dataType;
			this.service = service;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
	}

	@Getter
	public static class Response {
		private String stationId;
	    private String stationName;
	    private Double stationLatitude;
	    private Double stationLongitude;
	    private Integer rackTotCnt;
	    private Integer parkingBikeTotCnt;
	    private Integer shared;

	    @Builder
	    public Response(String stationId, String stationName, Double stationLatitude, Double stationLongitude,
				Integer rackTotCnt, Integer parkingBikeTotCnt, Integer shared) {
			this.stationId = stationId;
			this.stationName = stationName;
			this.stationLatitude = stationLatitude;
			this.stationLongitude = stationLongitude;
			this.rackTotCnt = rackTotCnt;
			this.parkingBikeTotCnt = parkingBikeTotCnt;
			this.shared = shared;
		}
	}
}
