package com.toyseven.ymk.weather;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.common.dto.WeatherDto;
import com.toyseven.ymk.weather.finedust.FineDustService;
import com.toyseven.ymk.weather.temperature.TemperatureService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final TemperatureService temperatureService;
    private final FineDustService fineDustService;

    @GetMapping(value = "/weather")
    public ResponseEntity<WeatherDto.Response> getWeatherInfo(WeatherDto.Request weatherRequest) {
        String fineDust = fineDustService.getFineDustInfo(weatherRequest);
//        JSONObject weather = temperatureService.getTemperature(weatherRequest);
        JSONObject weather = new JSONObject();
        weather.put("description", "2022/01/28 현재 공공데이터 포탈의 날씨 api 서비스가 종료되어 지원 불가능합니다.");
        weather.put("plan", "향 후 지역정보(지역구, 지역명)등을 위경도 데이터로 변환하여 위성조회 api를 사용 할 계획입니다.");

        WeatherDto.Response weatherResponse = WeatherDto.Response.builder().fineDust(fineDust).weather(weather).build();

        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }
}
