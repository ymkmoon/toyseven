package com.toyseven.ymk.common.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStationInformationEntity is a Querydsl query type for StationInformationEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStationInformationEntity extends EntityPathBase<StationInformationEntity> {

    private static final long serialVersionUID = -2005273145L;

    public static final QStationInformationEntity stationInformationEntity = new QStationInformationEntity("stationInformationEntity");

    public final NumberPath<Integer> parkingBikeTotCnt = createNumber("parkingBikeTotCnt", Integer.class);

    public final NumberPath<Integer> rackTotCnt = createNumber("rackTotCnt", Integer.class);

    public final NumberPath<Integer> shared = createNumber("shared", Integer.class);

    public final StringPath stationId = createString("stationId");

    public final NumberPath<Double> stationLatitude = createNumber("stationLatitude", Double.class);

    public final NumberPath<Double> stationLongitude = createNumber("stationLongitude", Double.class);

    public final StringPath stationName = createString("stationName");

    public QStationInformationEntity(String variable) {
        super(StationInformationEntity.class, forVariable(variable));
    }

    public QStationInformationEntity(Path<? extends StationInformationEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStationInformationEntity(PathMetadata metadata) {
        super(StationInformationEntity.class, metadata);
    }

}

