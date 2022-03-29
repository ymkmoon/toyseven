package com.toyseven.ymk.weather.temperature;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.toyseven.ymk.weather.WeatherDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TemperatureServiceImpl implements TemperatureService {
    private static final String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService";
    private final TemperatureParam temperatureParam;

    @SuppressWarnings("unchecked")
    @Override
	public JSONObject getTemperature(WeatherDto.Request weatherRequest) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm"));
        int tmpTime = Integer.parseInt(currentTime);
        
        if(tmpTime >= 0 && tmpTime < 230) {
            currentDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            currentTime = "2300";
        } else if (tmpTime >= 230 && tmpTime < 530) {
            currentTime = "0200";
        } else if (tmpTime >= 530 && tmpTime < 830) {
            currentTime = "0500";
        } else if (tmpTime >= 830 && tmpTime < 1130) {
            currentTime = "0800";
        } else if (tmpTime >= 1130 && tmpTime < 1430) {
            currentTime = "1100";
        } else if (tmpTime >= 1430 && tmpTime < 1730) {
            currentTime = "1400";
        } else if (tmpTime >= 1730 && tmpTime < 2030) {
            currentTime = "1700";
        } else if (tmpTime >= 2030 && tmpTime < 2330) {
            currentTime = "2000";
        }

        temperatureParam.setBaseDate(currentDate);
        temperatureParam.setBaseTime(currentTime);
        temperatureParam.setPageNo(1);
        temperatureParam.setNumOfRows(10);
        temperatureParam.setServiceKey(temperatureParam.getServiceKey());

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        ResponseEntity<JSONObject> response = wc.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getVilageFcst")
                        .queryParam("serviceKey", temperatureParam.getServiceKey())
                        .queryParam("dataType", temperatureParam.getDataType())
                        .queryParam("nx", weatherRequest.getNx())
                        .queryParam("ny", weatherRequest.getNy())
                        .queryParam("pageNo", temperatureParam.getPageNo())
                        .queryParam("numOfRows", temperatureParam.getNumOfRows())
                        .queryParam("base_date", temperatureParam.getBaseDate())
                        .queryParam("base_time", temperatureParam.getBaseTime()).build()
                ).accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(JSONObject.class)
                .block();
        
        Map<String, Object> responseData = ((Map<String, Object>)response.getBody().get("response"))
        		.entrySet().stream()
        		.filter(e -> e.getKey().equals("body"))
        		.collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
        
        List<Map<String, Object>> items = (List<Map<String, Object>>)(((Map<String, Object>)responseData.get("body")).get("items"));

        JSONObject temperature = new JSONObject();
        for (Map<String, Object> item : items) {
            if(item.get("category").equals("POP")) {
                temperature.put("precipitation", Integer.parseInt(item.get("fcstValue").toString()));
            }
            if(item.get("category").equals("T3H")) {
                temperature.put("temperature", Integer.parseInt(item.get("fcstValue").toString()));
            }
        }

        return temperature;
    }
}
