package com.toyseven.ymk.common.dto;

import javax.validation.constraints.NotEmpty;

import org.json.simple.JSONObject;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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

	    @NotNull
	    private int fineDust;
	    @NotNull
	    private JSONObject weather;
	}

}
