package com.toyseven.ymk.common.util;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ToysevenCommonUtil {
	
	/**
	 * response 의 null 체크와 status 체크
	 * @param response
	 * @return
	 */
	public static boolean isSuccessResponse(ResponseEntity<?> response) {
		return response != null && response.getStatusCode().is2xxSuccessful();
	}
	
	private static final Path CURRENT_PATH = Paths.get("src/main/java/com/toyseven/ymk/station");

	public static List<StationInformationEntity> getStationsFromJsonArrayFile(String filePath) {
		JSONArray stationArray = (JSONArray)readJsonFile(filePath);
		try {
			return new ObjectMapper().readValue(stationArray.toString(), new TypeReference<List<StationInformationEntity>>() {});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Object readJsonFile(String filePath) {
		try {
			JSONParser parser = new JSONParser();
			Path p = Paths.get(CURRENT_PATH.toString(), filePath);
			return parser.parse(new FileReader(p.toString()));
		} catch (IOException | ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
