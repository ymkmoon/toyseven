package com.toyseven.ymk.common.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import jakarta.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRefreshTokenEntity is a Querydsl query type for RefreshTokenEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRefreshTokenEntity extends EntityPathBase<RefreshTokenEntity> {

    private static final long serialVersionUID = 984837837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRefreshTokenEntity refreshTokenEntity = new QRefreshTokenEntity("refreshTokenEntity");

    public final com.toyseven.ymk.common.model.QBaseTimeEntity _super = new com.toyseven.ymk.common.model.QBaseTimeEntity(this);

    public final QAdminEntity adminId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRefreshTokenEntity(String variable) {
        this(RefreshTokenEntity.class, forVariable(variable), INITS);
    }

    public QRefreshTokenEntity(Path<? extends RefreshTokenEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRefreshTokenEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRefreshTokenEntity(PathMetadata metadata, PathInits inits) {
        this(RefreshTokenEntity.class, metadata, inits);
    }

    public QRefreshTokenEntity(Class<? extends RefreshTokenEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adminId = inits.isInitialized("adminId") ? new QAdminEntity(forProperty("adminId"), inits.get("adminId")) : null;
    }

}

