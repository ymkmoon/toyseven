package com.toyseven.ymk.station;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
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
import com.toyseven.ymk.common.search.StationSearchCondition;

import lombok.RequiredArgsConstructor;

@EnableScheduling
@RequiredArgsConstructor
@RestController
@RequestMapping("stations")
@Validated
public class StationController {

	private final StationService stationService;

	@GetMapping("/v1")
    public ResponseEntity<List<StationInformationDto.Response>> getStationsVer1(
    		@RequestParam(name="offset") @NotNull long offset,
    		@RequestParam(name="limit") @NotNull int limit,
    		Sort sort) {
    	Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        List<StationInformationDto.Response> stations = stationService.getAllStations(pageable);
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }
	
	// http://127.0.0.1:8000/toyseven/stations/v2?offset=0&limit=1000&sort=stationId,ASC&stationId=ST-801&stationName=2318. 삼성중앙역4번출구(문화센터더 리빌)
	@GetMapping("/v2")
    public ResponseEntity<Page<StationInformationDto.Response>> getStationsVer2(
    		StationSearchCondition condition,
    		@RequestParam(name="offset") @NotNull long offset,
    		@RequestParam(name="limit") @NotNull int limit,
    		Sort sort) {
    	Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        Page<StationInformationDto.Response> stations = stationService.getStationsSearchable(pageable, condition);
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<StationInformationDto.Response>> searchStations(
    		@RequestParam(name="name") @NotBlank String name,
    		@RequestParam(name="offset") @NotNull long offset,
    		@RequestParam(name="limit") @NotNull int limit,
    		Sort sort) {
    	Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        List<StationInformationDto.Response> stations = stationService.getStationByStationName(name, pageable);
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
