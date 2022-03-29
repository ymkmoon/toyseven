package com.toyseven.ymk.weather.finedust;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.WeatherDto;

@Transactional
public interface FineDustService {
	int getFineDustInfo(WeatherDto.Request weatherRequest);
}
