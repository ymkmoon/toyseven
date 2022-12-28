package com.toyseven.ymk.station;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.common.OffsetBasedPageRequest;
import com.toyseven.ymk.common.dto.StationInformationDto;

import lombok.RequiredArgsConstructor;

@EnableScheduling
@RequiredArgsConstructor
@RestController
@RequestMapping("stations")
@Validated
public class StationController {

	private final StationService stationService;

    @GetMapping()
    public ResponseEntity<List<StationInformationDto.Response>> getStations(
    		@RequestParam(name="offset") @NotNull long offset,
    		@RequestParam(name="limit") @NotNull int limit,
    		Sort sort) {
    	Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        List<StationInformationDto.Response> stations = stationService.getAllStations(pageable);
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<StationInformationDto.Response>> searchStations(
    		@RequestParam(name="name") @NotBlank String name) {
        List<StationInformationDto.Response> stations = stationService.getStationByStationName(name);
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
