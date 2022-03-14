package com.toyseven.ymk.weather.finedust;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.weather.WeatherRequest;

@Transactional
public interface FineDustService {
	int getFineDustInfo(WeatherRequest weatherRequest);
}
