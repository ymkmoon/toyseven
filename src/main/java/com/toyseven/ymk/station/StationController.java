package com.toyseven.ymk.station;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.common.dto.station.StationInformationDto;

import lombok.RequiredArgsConstructor;

@EnableScheduling
@RequiredArgsConstructor
@RestController
@RequestMapping("stations")
public class StationController {

    private final StationService stationService;

    @GetMapping()
    public ResponseEntity<List<StationInformationDto.Response>> getStations() {
        List<StationInformationDto.Response> stations = stationService.findAll();
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<StationInformationDto.Response>> searchStations(@RequestParam(value = "name") String name) {
        List<StationInformationDto.Response> stations = stationService.findByStationName(name);
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

//    @GetMapping(value = "/save")
//    public ResponseEntity<StationInformationEntity> saveStations() {
//    	List<StationInformationEntity> stations = stationService.getStations();
//    	if(stations != null) {
//    		stationService.save(stations);
//    		return new ResponseEntity<>(HttpStatus.OK);
//    	}
//    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}
