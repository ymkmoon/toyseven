package com.toyseven.ymk.voc.question;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyseven.ymk.common.dto.QVocQuestionDto_Response;
import com.toyseven.ymk.common.dto.VocQuestionDto;
import com.toyseven.ymk.common.model.entity.QVocQuestionEntity;
import com.toyseven.ymk.common.search.VocQuestionSearchCondition;
import com.toyseven.ymk.common.util.QuerydslUtil;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VocQuestionRepositoryCustom {
	
	private final JPAQueryFactory queryFactory;
	private static final QVocQuestionEntity question = QVocQuestionEntity.vocQuestionEntity;
	
	public Page<VocQuestionDto.Response> searchVocQuestion(final VocQuestionSearchCondition condition, final Pageable pageable) {
		
		List<OrderSpecifier<?>> orders = getAllOrderSpecifiers();
		
    	QueryResults<VocQuestionDto.Response> result = queryFactory
    			.select(
					new QVocQuestionDto_Response(
							question.id,
							question.category.displayName,
							question.title,
							question.content,
							
							question.email,
							question.username,
		    				question.stationId.stationId,
		    				question.needReply,
		    				
		    				question.createdAt,
		    				question.updatedAt,
		    				question.active
					)
    			)
    			.from(question)
    			.where(
					stationIdEq(condition.getStationId()),
					titleEq(condition.getTitle()),
					usernameEq(condition.getUsername()),
					emailEq(condition.getEmail()),
					categoryIdEq(condition.getCategoryId())
				)
    			.offset(pageable.getOffset())
    			.limit(pageable.getPageSize())
    			.orderBy(orders.stream().toArray(OrderSpecifier[]::new))
    			.fetchResults();
        
    	List<VocQuestionDto.Response> content = result.getResults();
//    	long total = result.getTotal();
//    	return new PageImpl<>(content, pageable, total);
    	
    	return new PageImpl<>(content);
    }

	// BooleanExpression으로 해야 나중에 Composition이 가능
	private BooleanExpression categoryIdEq(final String categoryId) { 
        return StringUtils.hasText(categoryId) ? question.category.displayName.eq(categoryId) : null;
    }
	
	private BooleanExpression titleEq(final String title) { 
		return StringUtils.hasText(title) ? question.title.eq(title) : null;
	}
	
	private BooleanExpression usernameEq(final String username) { 
		return StringUtils.hasText(username) ? question.username.eq(username) : null;
	}
	
	private BooleanExpression emailEq(final String email) { 
		return StringUtils.hasText(email) ? question.email.eq(email) : null;
	}
	
	private BooleanExpression stationIdEq(final String stationId) { 
        return StringUtils.hasText(stationId) ? question.stationId.stationId.eq(stationId) : null;
    }
	
	private List<OrderSpecifier<?>> getAllOrderSpecifiers() {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        OrderSpecifier<?> updatedAt = QuerydslUtil.getSortedColumn(Order.DESC, question, "updatedAt");
        orders.add(updatedAt);
        return orders;
    }
	

}
