package com.toyseven.ymk.weather.temperature;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.weather.WeatherRequest;

@Transactional
public interface TemperatureService {
	JSONObject getTemperature(WeatherRequest weatherRequest);
}
