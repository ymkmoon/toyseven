package com.toyseven.ymk.weather.finedust;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FineDustParam {
    @Value("${api.key.fineDust}")
    private String serviceKey;
    private final String returnType = "json";
    private final String dataTerm = "DAILY";
    private final String ver = "1.3";
    @NotBlank
    private String stationName;
    private int pageNo;
    private int numOfRows;
}
