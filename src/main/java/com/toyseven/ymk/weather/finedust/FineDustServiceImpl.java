package com.toyseven.ymk.weather.finedust;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.Exception.BusinessException;
import com.toyseven.ymk.weather.WeatherDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FineDustServiceImpl implements FineDustService {
    private static final String BASE_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc";
    private final FineDustParam fineDustParam;

    @SuppressWarnings("unchecked")
    @Override
	public int getFineDustInfo(WeatherDto.Request weatherRequest) {
        fineDustParam.setPageNo(1);
        fineDustParam.setNumOfRows(1);
        fineDustParam.setServiceKey(fineDustParam.getServiceKey());
        StringBuffer stationName = new StringBuffer();
		try {
			stationName.append(URLEncoder.encode(weatherRequest.getStationName(), "UTF-8"));
		} catch (final UnsupportedEncodingException e) {
			throw new BusinessException(e.getMessage(), ErrorCode.ENCODING_ERROR);
		}
        fineDustParam.setStationName(stationName.toString());

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();
        ResponseEntity<JSONObject> response = wc.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getMsrstnAcctoRltmMesureDnsty")
                        .queryParam("serviceKey", fineDustParam.getServiceKey())
                        .queryParam("stationName", fineDustParam.getStationName())
                        .queryParam("returnType", fineDustParam.getReturnType())
                        .queryParam("pageNo", fineDustParam.getPageNo())
                        .queryParam("numOfRows", fineDustParam.getNumOfRows())
                        .queryParam("dataTerm", fineDustParam.getDataTerm())
                        .queryParam("ver", fineDustParam.getVer()).build()
                ).headers(httpHeaders -> httpHeaders.add("Content-Type", "application/json;charset=UTF-8"))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(JSONObject.class)
                .block();
        
        Map<String, Object> responseData = ((Map<String, Object>)response.getBody().get("response"))
        		.entrySet().stream()
        		.filter(e -> e.getKey().equals("body"))
        		.collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
        
        List<Map<String, Object>> items = (List<Map<String, Object>>)(((Map<String, Object>)responseData.get("body")).get("items"));
        
        String fineDust = (String)StreamSupport.stream(items.spliterator(), false)
        		.map(item -> item)
        		.filter(item -> !item.isEmpty())
        		.filter(item -> !item.get("khaiValue").toString().isBlank())
        		.findFirst()
        		.map(item -> item.get("khaiValue"))
        		.orElse(0);

        return Integer.parseInt(fineDust);
    }
}
