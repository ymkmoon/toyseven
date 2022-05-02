package com.toyseven.ymk.weather.temperature;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.toyseven.ymk.common.ResponseEntityUtil;
import com.toyseven.ymk.common.WebClientUtil;
import com.toyseven.ymk.common.dto.WeatherDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TemperatureServiceImpl implements TemperatureService {
	
    private static final String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService";
    
    @Value("${api.key.weather}") 
    private String SERVICE_KEY;
    private static final String DATA_TYPE = "JSON";
    private static final String CURRENT_DATE = "currentDate";
    private static final String CURRENT_TIME = "currentTime";

    @SuppressWarnings("unchecked")
    @Override
	public JSONObject getTemperature(WeatherDto.Request weatherRequest) {
    	
    	WeatherDto.TemperatureRequest temperatureRequest = setTemperatureRequest(weatherRequest);

        WebClient wc = WebClientUtil.buildWebClient(BASE_URL, DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        ResponseEntity<JSONObject> response = ResponseEntityUtil.temperatureApi(wc, temperatureRequest);
        
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
    
    private WeatherDto.TemperatureRequest setTemperatureRequest(WeatherDto.Request weatherRequest) {
    	WeatherDto.TemperatureRequest request = new WeatherDto.TemperatureRequest();
    	Map<String, String> currentDateTime = getCurrentDateTime();

        request.setBaseDate(currentDateTime.get(CURRENT_DATE));
        request.setBaseTime(currentDateTime.get(CURRENT_TIME));
        request.setPageNo(1);
        request.setNumOfRows(10);
        request.setServiceKey(SERVICE_KEY);
        request.setDataType(DATA_TYPE);
        request.setNx(weatherRequest.getNx());
        request.setNy(weatherRequest.getNy());
        
        return request;
    }
    
    private Map<String, String> getCurrentDateTime() {
    	Map<String, String> currentDateTime = new HashMap<>();
    	
    	String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm"));
        int tmpTime = Integer.parseInt(currentTime);
        currentDateTime.put(CURRENT_DATE, currentDate);
        
        if(tmpTime >= 0 && tmpTime < 230) {
        	currentDateTime.remove(CURRENT_DATE);
            currentDateTime.put(CURRENT_DATE, LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            currentDateTime.put(CURRENT_TIME, "2300");
        } else if (tmpTime >= 230 && tmpTime < 530) {
            currentDateTime.put(CURRENT_TIME, "0200");
        } else if (tmpTime >= 530 && tmpTime < 830) {
            currentDateTime.put(CURRENT_TIME, "0500");
        } else if (tmpTime >= 830 && tmpTime < 1130) {
            currentDateTime.put(CURRENT_TIME, "0800");
        } else if (tmpTime >= 1130 && tmpTime < 1430) {
            currentDateTime.put(CURRENT_TIME, "1100");
        } else if (tmpTime >= 1430 && tmpTime < 1730) {
            currentDateTime.put(CURRENT_TIME, "1400");
        } else if (tmpTime >= 1730 && tmpTime < 2030) {
            currentDateTime.put(CURRENT_TIME, "1700");
        } else if (tmpTime >= 2030 && tmpTime < 2330) {
            currentDateTime.put(CURRENT_TIME, "2000");
        }
        
        return currentDateTime;
    }
    
}
