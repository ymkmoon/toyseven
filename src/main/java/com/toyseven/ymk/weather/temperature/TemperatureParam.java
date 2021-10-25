package com.toyseven.ymk.weather.temperature;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TemperatureParam {
    @Value("${api.key.weather}")
    private String serviceKey;
    private int pageNo;
    private int numOfRows;
    private final String dataType = "JSON";
    private String baseDate;
    private String baseTime;
    @NotBlank
    private int nx;
    @NotBlank
    private int ny;
}