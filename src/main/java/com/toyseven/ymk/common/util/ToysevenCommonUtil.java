package com.toyseven.ymk.common.util;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.exception.BusinessException;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ToysevenCommonUtil {
	
	/**
	 * response 의 null 체크와 status 체크
	 * @param response
	 * @return
	 */
	public static boolean isSuccessResponse(ResponseEntity<?> response) {
		return response != null && response.getStatusCode().is2xxSuccessful();
	}
	
//	private static final Path DATA_PATH = Paths.get("src/main/java/com/toyseven/ymk/data");
	private static final Path DATA_PATH = Paths.get("src/main/resources");

	public static List<StationInformationEntity> getStationsFromJsonArrayFile(String fileName) {
		JSONArray stationArray = (JSONArray)readJsonFile(fileName);
		try {
			return new ObjectMapper().readValue(stationArray.toString(), new TypeReference<List<StationInformationEntity>>() {});
		} catch (JsonProcessingException e) {
			throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR, e.getMessage());
		}
	}
	
	public static Object readJsonFile(String fileName) {
		try {
			JSONParser parser = new JSONParser();
			Path p = Paths.get(DATA_PATH.toString(), fileName+"123");
			return parser.parse(new FileReader(p.toString()));
		} catch (IOException | ParseException e) {
			throw new BusinessException(ErrorCode.READ_JSON_ERROR, e.getMessage());
		}
	}
	
	public static boolean isNotNullOrEmptyMap(Map <? , ?> map) {
	    return !(map == null || map.isEmpty()); // this method defined below
	}
	
	public boolean hasText(String str) {
		return StringUtils.hasText(str);
	}
}
