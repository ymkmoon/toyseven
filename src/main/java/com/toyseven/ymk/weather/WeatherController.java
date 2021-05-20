package com.toyseven.ymk.weather;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.weather.dto.request.WeatherRequest;
import com.toyseven.ymk.weather.dto.response.WeatherResponse;
import com.toyseven.ymk.weather.finedust.FineDustService;
import com.toyseven.ymk.weather.temperature.TemperatureService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final TemperatureService temperatureService;
    private final FineDustService fineDustService;

    @GetMapping(value = "/weather")
    public ResponseEntity<WeatherResponse> getWeatherInfo(WeatherRequest weatherRequest) {
        int fineDust = fineDustService.getFineDustInfo(weatherRequest);
        JSONObject weather = temperatureService.getTemperature(weatherRequest);

        WeatherResponse weatherResponse = new WeatherResponse();

        weatherResponse.setFineDust(fineDust);
        weatherResponse.setWeather(weather);

        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }
}
