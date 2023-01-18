package com.toyseven.ymk.common.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminEntity is a Querydsl query type for AdminEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdminEntity extends EntityPathBase<AdminEntity> {

    private static final long serialVersionUID = 1635264550L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdminEntity adminEntity = new QAdminEntity("adminEntity");

    public final com.toyseven.ymk.common.model.QBaseTimeEntity _super = new com.toyseven.ymk.common.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final QAdminRoleEntity role;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QAdminEntity(String variable) {
        this(AdminEntity.class, forVariable(variable), INITS);
    }

    public QAdminEntity(Path<? extends AdminEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdminEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdminEntity(PathMetadata metadata, PathInits inits) {
        this(AdminEntity.class, metadata, inits);
    }

    public QAdminEntity(Class<? extends AdminEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.role = inits.isInitialized("role") ? new QAdminRoleEntity(forProperty("role")) : null;
    }

}

