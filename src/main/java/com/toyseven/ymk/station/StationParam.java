package com.toyseven.ymk.station;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StationParam {
	@Value("${api.key.station}")
    private String serviceKey;
	private final String dataType = "JSON";
	private final String service = "bikeList";
	private Integer startIndex;
	private Integer endIndex;
	
}
