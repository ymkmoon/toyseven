package com.toyseven.ymk.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import org.json.simple.JSONObject;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	
	@Getter
	public static class Response {
	    private String fineDust;
	    private JSONObject weather;

	    @Builder
		public Response(String fineDust, JSONObject weather) {
			this.fineDust = fineDust;
			this.weather = weather;
		}
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
