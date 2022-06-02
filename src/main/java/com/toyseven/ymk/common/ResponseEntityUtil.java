package com.toyseven.ymk.common;

import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.dto.WeatherDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseEntityUtil {
	
	public static ResponseEntity<JSONObject> stationApi(WebClient webClient, StationInformationDto.Request stationRequest) {
		return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/"+stationRequest.getServiceKey())
                        .path("/"+stationRequest.getDataType())
                        .path("/"+stationRequest.getService())
                        .path("/"+stationRequest.getStartIndex())
                        .path("/"+stationRequest.getEndIndex())
                        .build()
                ).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(JSONObject.class)
                .block();
	}
	
	public static ResponseEntity<JSONObject> fineDustApi(WebClient webClient, WeatherDto.FineDustRequest fineDustRequest) {
		return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getMsrstnAcctoRltmMesureDnsty")
                        .queryParam("serviceKey", fineDustRequest.getServiceKey())
                        .queryParam("stationName", fineDustRequest.getStationName())
                        .queryParam("returnType", fineDustRequest.getReturnType())
                        .queryParam("pageNo", fineDustRequest.getPageNo())
                        .queryParam("numOfRows", fineDustRequest.getNumOfRows())
                        .queryParam("dataTerm", fineDustRequest.getDataTerm())
                        .queryParam("ver", fineDustRequest.getVer()).build()
                ).headers(httpHeaders -> httpHeaders.add("Content-Type", "application/json;charset=UTF-8"))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(JSONObject.class)
                .block();
	}
	
	public static ResponseEntity<JSONObject> temperatureApi(WebClient webClient, WeatherDto.TemperatureRequest temperatureRequest) {
		return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getVilageFcst")
                        .queryParam("serviceKey", temperatureRequest.getServiceKey())
                        .queryParam("dataType", temperatureRequest.getDataType())
                        .queryParam("nx", temperatureRequest.getNx())
                        .queryParam("ny", temperatureRequest.getNy())
                        .queryParam("pageNo", temperatureRequest.getPageNo())
                        .queryParam("numOfRows", temperatureRequest.getNumOfRows())
                        .queryParam("base_date", temperatureRequest.getBaseDate())
                        .queryParam("base_time", temperatureRequest.getBaseTime()).build()
                ).accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(JSONObject.class)
                .block();
	}
	
	
	public static ResponseEntity<JSONObject> cognitoRefreshToken(WebClient webClient, MultiValueMap<String, String> params) {
		return webClient.post()
				.uri(uriBuilder -> uriBuilder
						.path("/oauth2")
						.path("/token")
						.build())
				.headers(httpHeaders -> 
				httpHeaders.add("Content-Type", "application/x-www-form-urlencoded"))
				.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(StandardCharsets.UTF_8)
				.bodyValue(params)
				.retrieve()
				.onStatus(status -> !status.is2xxSuccessful() , clientResponse ->
				clientResponse.bodyToMono(String.class)
				.map(body -> new Exception("Token_Update_Error_"+body)))
				.toEntity(JSONObject.class)
				.block();
	}
}
