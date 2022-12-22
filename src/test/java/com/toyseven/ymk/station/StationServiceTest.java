package com.toyseven.ymk.station;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.OffsetBasedPageRequest;
import com.toyseven.ymk.common.ToysevenUnitTestUtil;
import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;

/**
 * @author YMK
 *
 */
@ExtendWith(MockitoExtension.class)
class StationServiceTest {
	
	@Mock StationRepository stationRepository;
	@Autowired ObjectMapper objectMapper;
	@InjectMocks StationServiceImpl stationServiceImpl;
	List<StationInformationEntity> stations;
	StationInformationEntity station;
	StationInformationDto.Request stationRequest;
	
	@BeforeEach
	void setup() {
		this.stations = ToysevenUnitTestUtil.getStationsFromJsonArrayFile("StationFindAll.json");
		this.station = StationInformationEntity.builder()
				.stationName("103. 망원역 2번출구 앞")
				.stationLatitude(37.55495071)
				.stationLongitude(126.91083527)
				.rackTotCnt(14)
				.parkingBikeTotCnt(19)
				.shared(136)
				.build();
		this.stationRequest = StationInformationDto.Request.builder()
				.build();
	}
	
	@Test
	@DisplayName("Station 목록 조회 성공 테스트")
	void Should_Return_Stations_When_Find_All() {
		Mockito.when(stationRepository.findAll()).thenReturn(stations);
		Pageable pageable = new OffsetBasedPageRequest(1, 1);
		List<StationInformationDto.Response> response = stationServiceImpl.getAllStations(pageable);
		assertThat(response, is(not(empty())));
	}
	
	@Test
	@DisplayName("Station 검색 성공 테스트")
	void Should_Return_Stations_When_Find_By_Station_Name_Containing() {
		List<StationInformationEntity> stationFindByStationNameContaining = new ArrayList<>();
		stationFindByStationNameContaining.add(station);
		Mockito.when(stationRepository.findByStationNameContaining("103. 망원역 2번출구 앞")).thenReturn(stationFindByStationNameContaining);
		List<StationInformationDto.Response> response = stationServiceImpl.getStationByStationName("103. 망원역 2번출구 앞");
		assertThat(response, is(not(empty())));
	}
	
//	@Test
//	@DisplayName("Station 요청 성공 테스트")
//	void Should_Request_Station_Api() throws Exception {
//		
////		StationServiceImpl stationServiceImplForPrivate = new StationServiceImpl(stationRepositoryForPrivate, objectMapper);
//		Method reuqestToStation = stationServiceImpl.getClass().getDeclaredMethod("requestToStation", int.class);
//		reuqestToStation.setAccessible(true);
//		Mockito.when(reuqestToStation.invoke(stationServiceImpl, 1)).thenReturn(stations);
//		Mockito.when(reuqestToStation.invoke(stationServiceImpl, 1001)).thenReturn(stations);
//		Mockito.when(reuqestToStation.invoke(stationServiceImpl, 2001)).thenReturn(stations);
//		
//		stationServiceImpl.getStations();
//	}
	
	

}
