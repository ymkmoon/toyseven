package com.toyseven.ymk.common.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminRoleEntity is a Querydsl query type for AdminRoleEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdminRoleEntity extends EntityPathBase<AdminRoleEntity> {

    private static final long serialVersionUID = -1275065668L;

    public static final QAdminRoleEntity adminRoleEntity = new QAdminRoleEntity("adminRoleEntity");

    public final StringPath displayName = createString("displayName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QAdminRoleEntity(String variable) {
        super(AdminRoleEntity.class, forVariable(variable));
    }

    public QAdminRoleEntity(Path<? extends AdminRoleEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminRoleEntity(PathMetadata metadata) {
        super(AdminRoleEntity.class, metadata);
    }

}

