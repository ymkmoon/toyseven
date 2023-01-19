package com.toyseven.ymk.common.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.toyseven.ymk.common.dto.QStationInformationDto_Response is a Querydsl Projection type for Response
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QStationInformationDto_Response extends ConstructorExpression<StationInformationDto.Response> {

    private static final long serialVersionUID = -491410575L;

    public QStationInformationDto_Response(com.querydsl.core.types.Expression<String> stationId, com.querydsl.core.types.Expression<String> stationName, com.querydsl.core.types.Expression<Double> stationLatitude, com.querydsl.core.types.Expression<Double> stationLongitude, com.querydsl.core.types.Expression<Integer> rackTotCnt, com.querydsl.core.types.Expression<Integer> parkingBikeTotCnt, com.querydsl.core.types.Expression<Integer> shared) {
        super(StationInformationDto.Response.class, new Class<?>[]{String.class, String.class, double.class, double.class, int.class, int.class, int.class}, stationId, stationName, stationLatitude, stationLongitude, rackTotCnt, parkingBikeTotCnt, shared);
    }

}

