package com.toyseven.ymk.common.dto.station;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StationInformationResponse {

	private String stationId;
	
    private String stationName;

    private Double stationLatitude;

    private Double stationLongitude;

    private Integer rackTotCnt;

    private Integer parkingBikeTotCnt;

    private Integer shared;
}
