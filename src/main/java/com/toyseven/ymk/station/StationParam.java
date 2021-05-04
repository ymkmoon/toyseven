package com.toyseven.ymk.station;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StationParam {
	@Value("${api.key.station}")
    private String serviceKey;
	private final String dataType = "JSON";
	private final String service = "bikeList";
	private final Integer startIndex = 1;
	private final Integer endIndex = 1000;
	
}
