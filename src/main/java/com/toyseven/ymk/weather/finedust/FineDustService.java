package com.toyseven.ymk.weather.finedust;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.WeatherDto;

@Transactional
public interface FineDustService {
	String getFineDustInfo(WeatherDto.Request weatherRequest);
}
