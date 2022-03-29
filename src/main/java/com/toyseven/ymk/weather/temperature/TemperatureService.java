package com.toyseven.ymk.weather.temperature;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.weather.WeatherDto;

@Transactional
public interface TemperatureService {
	JSONObject getTemperature(WeatherDto.Request weatherRequest);
}
