package com.toyseven.ymk.common.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import jakarta.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVocAnswerEntity is a Querydsl query type for VocAnswerEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVocAnswerEntity extends EntityPathBase<VocAnswerEntity> {

    private static final long serialVersionUID = 986392895L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVocAnswerEntity vocAnswerEntity = new QVocAnswerEntity("vocAnswerEntity");

    public final com.toyseven.ymk.common.model.QBaseTimeEntity _super = new com.toyseven.ymk.common.model.QBaseTimeEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final QAdminEntity adminId;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QVocQuestionEntity questionId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QVocAnswerEntity(String variable) {
        this(VocAnswerEntity.class, forVariable(variable), INITS);
    }

    public QVocAnswerEntity(Path<? extends VocAnswerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVocAnswerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVocAnswerEntity(PathMetadata metadata, PathInits inits) {
        this(VocAnswerEntity.class, metadata, inits);
    }

    public QVocAnswerEntity(Class<? extends VocAnswerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adminId = inits.isInitialized("adminId") ? new QAdminEntity(forProperty("adminId"), inits.get("adminId")) : null;
        this.questionId = inits.isInitialized("questionId") ? new QVocQuestionEntity(forProperty("questionId"), inits.get("questionId")) : null;
    }

}

