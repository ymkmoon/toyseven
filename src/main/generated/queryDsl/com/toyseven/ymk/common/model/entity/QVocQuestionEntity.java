package com.toyseven.ymk.common.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVocQuestionEntity is a Querydsl query type for VocQuestionEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVocQuestionEntity extends EntityPathBase<VocQuestionEntity> {

    private static final long serialVersionUID = 1840734183L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVocQuestionEntity vocQuestionEntity = new QVocQuestionEntity("vocQuestionEntity");

    public final com.toyseven.ymk.common.model.QBaseTimeEntity _super = new com.toyseven.ymk.common.model.QBaseTimeEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final QVocCategoryEntity category;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> needReply = createNumber("needReply", Integer.class);

    public final QStationInformationEntity stationId;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QVocQuestionEntity(String variable) {
        this(VocQuestionEntity.class, forVariable(variable), INITS);
    }

    public QVocQuestionEntity(Path<? extends VocQuestionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVocQuestionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVocQuestionEntity(PathMetadata metadata, PathInits inits) {
        this(VocQuestionEntity.class, metadata, inits);
    }

    public QVocQuestionEntity(Class<? extends VocQuestionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QVocCategoryEntity(forProperty("category")) : null;
        this.stationId = inits.isInitialized("stationId") ? new QStationInformationEntity(forProperty("stationId")) : null;
    }

}

