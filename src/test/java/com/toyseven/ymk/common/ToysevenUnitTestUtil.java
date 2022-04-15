package com.toyseven.ymk.common;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

public class ToysevenUnitTestUtil {

	private static final Path CURRENT_PATH = Paths.get("src/test/java/com/toyseven/ymk/data");

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
