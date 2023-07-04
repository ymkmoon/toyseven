package com.toyseven.ymk.common.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import jakarta.annotation.Generated;

/**
 * com.toyseven.ymk.common.dto.QVocQuestionDto_Response is a Querydsl Projection type for Response
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QVocQuestionDto_Response extends ConstructorExpression<VocQuestionDto.Response> {

    private static final long serialVersionUID = -1531386313L;

    public QVocQuestionDto_Response(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> category, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<String> stationId, com.querydsl.core.types.Expression<Integer> needReply, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt, com.querydsl.core.types.Expression<Boolean> active) {
        super(VocQuestionDto.Response.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, int.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, boolean.class}, id, category, title, content, email, username, stationId, needReply, createdAt, updatedAt, active);
    }

}

