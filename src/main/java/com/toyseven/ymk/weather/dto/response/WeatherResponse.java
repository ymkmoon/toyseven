package com.toyseven.ymk.weather.dto.response;

import org.json.simple.JSONObject;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

    @NotNull
    private int fineDust;
    @NotNull
    private JSONObject weather;
}
