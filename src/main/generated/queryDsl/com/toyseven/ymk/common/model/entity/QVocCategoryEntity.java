package com.toyseven.ymk.common.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVocCategoryEntity is a Querydsl query type for VocCategoryEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVocCategoryEntity extends EntityPathBase<VocCategoryEntity> {

    private static final long serialVersionUID = 1336454911L;

    public static final QVocCategoryEntity vocCategoryEntity = new QVocCategoryEntity("vocCategoryEntity");

    public final StringPath displayName = createString("displayName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QVocCategoryEntity(String variable) {
        super(VocCategoryEntity.class, forVariable(variable));
    }

    public QVocCategoryEntity(Path<? extends VocCategoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVocCategoryEntity(PathMetadata metadata) {
        super(VocCategoryEntity.class, metadata);
    }

}

