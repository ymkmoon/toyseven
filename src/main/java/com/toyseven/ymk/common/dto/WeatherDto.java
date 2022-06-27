package com.toyseven.ymk.common.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class WeatherDto {

	@Getter
	@Setter
	public static class Request {
	    @NotEmpty
	    private int nx;
	    @NotEmpty
	    private int ny;
	    @NotEmpty
	    private String stationName;
	}
	
	@Builder
	@Getter
	@AllArgsConstructor
	public static class Response {

	    private String fineDust;
	    private JSONObject weather;
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class FineDustRequest {
	    private String serviceKey;
	    private String returnType;
	    private String dataTerm;
	    private String ver;
	    @NotBlank private String stationName;
	    private int pageNo;
	    private int numOfRows;
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class TemperatureRequest {
	    private String serviceKey;
	    private int pageNo;
	    private int numOfRows;
	    private String dataType;
	    private String baseDate;
	    private String baseTime;
	    @NotBlank private int nx;
	    @NotBlank private int ny;
	}

}
