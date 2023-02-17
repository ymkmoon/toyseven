package com.toyseven.ymk.station;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.OffsetBasedPageRequest;
import com.toyseven.ymk.common.ToysevenUnitTestUtil;
import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.search.StationSearchCondition;

/**
 * @author YMK
 *
 */
@ExtendWith(MockitoExtension.class)
class StationServiceTest {
	
	@Mock StationRepository stationRepository;
	@Mock StationRepositoryCustom customRepository;
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
	@DisplayName("Station 목록 조회 v1 성공 테스트")
	void Should_Return_Stations_When_Find_All_V1() {
		Page<StationInformationEntity> page = new PageImpl<>(stations);
		Pageable pageable = new OffsetBasedPageRequest(0, 9999);
		
		Mockito.when(stationRepository.findAll(pageable)).thenReturn(page);		
		List<StationInformationDto.Response> response = stationServiceImpl.getAllStations(pageable);
		assertThat(response, is(not(empty())));
	}
	
	@Test
	@DisplayName("Station 목록 조회 v2 성공 테스트")
	void Should_Return_Stations_When_Find_All_V2() {
		Page<StationInformationEntity> page = new PageImpl<>(stations);
		List<StationInformationDto.Response> result = page.map(StationInformationEntity::toStationInformationResponse).toList();
		Page<StationInformationDto.Response> pageToDto = new PageImpl<>(result);
		
		Pageable pageable = new OffsetBasedPageRequest(0, 9999);
		StationSearchCondition condition = new StationSearchCondition();
		condition.setStationId("ST-4");
		condition.setStationName("102. 망원역 1번출구 앞");
		
		Mockito.when(customRepository.searchStations(condition, pageable)).thenReturn(pageToDto);		
		Page<StationInformationDto.Response> response = stationServiceImpl.getStationsSearchable(pageable, condition);
		assertThat(response.getContent(), is(not(empty())));
	}
	
	@Test
	@DisplayName("Station 검색 성공 테스트")
	void Should_Return_Stations_When_Find_By_Station_Name_Containing() {
		List<StationInformationEntity> stationFindByStationNameContaining = new ArrayList<>();
		stationFindByStationNameContaining.add(station);
		
		Page<StationInformationEntity> page = new PageImpl<>(stationFindByStationNameContaining);
		Pageable pageable = new OffsetBasedPageRequest(1, 1, null);
		
		String stationName = "103. 망원역 2번출구 앞";
		Mockito.when(stationRepository.findByStationNameContaining(stationName, pageable)).thenReturn(page);
		List<StationInformationDto.Response> response = stationServiceImpl.getStationByStationName(stationName, pageable);
		assertThat(response, is(not(empty())));
	}
	
	@Test
	@DisplayName("Station 검색 실패 테스트")
	void Should_Throws_Exception_When_Find_By_Station_Name_Containing() {
		List<StationInformationEntity> stationFindByStationNameContaining = new ArrayList<>();
		
		Page<StationInformationEntity> page = new PageImpl<>(stationFindByStationNameContaining);
		Pageable pageable = new OffsetBasedPageRequest(1, 1, null);
		
		String stationName = "103. 망원역 2번출구 앞";
		Mockito.when(stationRepository.findByStationNameContaining(stationName, pageable)).thenReturn(page);
		
		assertThatThrownBy(() -> { 
				stationServiceImpl.getStationByStationName(stationName, pageable); 
		}).isInstanceOf(NoSuchElementException.class)
		.hasMessage(ErrorCode.NO_SUCH_ELEMENT.getDetail());
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
