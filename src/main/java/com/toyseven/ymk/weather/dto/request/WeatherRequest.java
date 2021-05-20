package com.toyseven.ymk.weather.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherRequest {

    @NotEmpty
    private int nx;
    @NotEmpty
    private int ny;
    @NotEmpty
    private String stationName;
}
