package com.toyseven.ymk.weather.finedust;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.toyseven.ymk.common.ResponseEntityComponent;
import com.toyseven.ymk.common.WebClientFactory;
import com.toyseven.ymk.common.dto.WeatherDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FineDustServiceImpl implements FineDustService {
	
	private final ResponseEntityComponent responseEntityComponent;
	
    private static final String BASE_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc";
    
    @Value("${api.key.fineDust}") 
    private String SERVICE_KEY;
    private static final String RETURN_TYPE = "json";
    private static final String DATA_TERM = "DAILY";
    private static final String VER = "1.3";

    @SuppressWarnings("unchecked")
    @Override
	public String getFineDustInfo(WeatherDto.Request weatherRequest) {
		WeatherDto.FineDustRequest fineDustRequest = setFineDustRequest(weatherRequest.getStationName());
        WebClient wc = new WebClientFactory(BASE_URL, DefaultUriBuilderFactory.EncodingMode.NONE).buildWebClient();
        ResponseEntity<JSONObject> response = responseEntityComponent.fineDustApi(wc, fineDustRequest);
        
        Map<String, Object> responseData = ((Map<String, Object>)response.getBody().get("response"))
        		.entrySet().stream()
        		.filter(e -> e.getKey().equals("body"))
        		.collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
        
        List<Map<String, Object>> items = (List<Map<String, Object>>)(((Map<String, Object>)responseData.get("body")).get("items"));
        
        return (String)StreamSupport.stream(items.spliterator(), false)
        		.map(item -> item)
        		.filter(item -> !item.isEmpty())
        		.filter(item -> !item.get("khaiValue").toString().isEmpty())
        		.findFirst()
        		.map(item -> item.get("khaiValue"))
        		.orElse("0");
    }
    
    private WeatherDto.FineDustRequest setFineDustRequest(String stationName) {
    	WeatherDto.FineDustRequest request = new WeatherDto.FineDustRequest();
    	try {
			request.setStationName(URLEncoder.encode(stationName, "UTF-8"));
		} catch (final UnsupportedEncodingException e) {
			throw new BusinessException(ErrorCode.STATION_NAME_ENCODING_ERROR, e.getMessage());
		}
    	request.setPageNo(1);
        request.setNumOfRows(1);
        request.setServiceKey(SERVICE_KEY);
        request.setReturnType(RETURN_TYPE);
        request.setDataTerm(DATA_TERM);
        request.setVer(VER);
        
        return request;
    }
}
