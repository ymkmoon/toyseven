package com.toyseven.ymk.station;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyseven.ymk.common.dto.QStationInformationDto_Response;
import com.toyseven.ymk.common.dto.StationInformationDto;
import com.toyseven.ymk.common.model.entity.QStationInformationEntity;
import com.toyseven.ymk.common.search.StationSearchCondition;
import com.toyseven.ymk.common.util.QuerydslUtil;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StationRepositoryCustom {
	
	private final JPAQueryFactory queryFactory;
	private final QStationInformationEntity station = QStationInformationEntity.stationInformationEntity;
	
	public Page<StationInformationDto.Response> searchStations(final StationSearchCondition condition, final Pageable pageable) {
		
		List<OrderSpecifier<?>> ORDERS = getAllOrderSpecifiers(pageable);
		
    	QueryResults<StationInformationDto.Response> result = queryFactory
    			.select(
					new QStationInformationDto_Response(
							station.stationId,
							station.stationName,
							station.stationLatitude,
							station.stationLongitude,
							station.rackTotCnt,
							station.parkingBikeTotCnt,
							station.shared
					)
    			)
    			.from(station)
    			.where(
					stationIdEq(condition.getStationId()),
					stationNameEq(condition.getStationName())
				)
//    			.offset(pageable.getOffset())
    			.limit(pageable.getPageSize())
    			.orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
    			.fetchResults();
        
    	List<StationInformationDto.Response> content = result.getResults();
//    	long total = result.getTotal();
//    	return new PageImpl<>(content, pageable, total);
    	
    	return new PageImpl<>(content);
    }

	// BooleanExpression으로 해야 나중에 Composition이 가능
	private BooleanExpression stationIdEq(final String stationId) { 
        return StringUtils.hasText(stationId) ? station.stationId.eq(stationId) : null;
    }
	
	private BooleanExpression stationNameEq(final String stationName) { 
		return StringUtils.hasText(stationName) ? station.stationName.eq(stationName) : null;
	}
	
	 private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> ORDERS = new ArrayList<>();

        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "stationId":
                        OrderSpecifier<?> stationId = QuerydslUtil.getSortedColumn(direction, station, "stationId");
                        ORDERS.add(stationId);
                        break;
                    default:
                        break;
                }
            }
        }

        return ORDERS;
    }

}
