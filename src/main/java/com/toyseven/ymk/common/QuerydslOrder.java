package com.toyseven.ymk.common;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuerydslOrder {
	
	private final Order order;
	private final Path<?> parent;
	private final String fieldName; 

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OrderSpecifier<?> getSortedColumn() {
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }
}
