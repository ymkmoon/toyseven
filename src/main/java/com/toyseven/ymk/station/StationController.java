package com.toyseven.ymk.station;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.model.StationInformation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("stations")
public class StationController {

    private final StationService stationService;

    @GetMapping()
    public ResponseEntity<List<StationInformation>> getStations() {
        List<StationInformation> stations = stationService.findAll();
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Optional<List<StationInformation>>> searchStations(@RequestParam("name") String stationName) {
        Optional<List<StationInformation>> stations = stationService.findByStationName(stationName);
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }
    
    @GetMapping(value = "/save")
    public ResponseEntity<?> saveStations() {
    	List<StationInformation> stations = stationService.getStationList();
    	if(stations != null) {
    		stationService.save(stations);
    		return new ResponseEntity<StationInformation>(HttpStatus.OK);
    	}
    	return new ResponseEntity<StationInformation>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
