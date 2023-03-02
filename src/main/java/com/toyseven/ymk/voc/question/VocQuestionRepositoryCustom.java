package com.toyseven.ymk.voc.question;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyseven.ymk.common.Constants;
import com.toyseven.ymk.common.QuerydslOrder;
import com.toyseven.ymk.common.dto.QVocQuestionDto_Response;
import com.toyseven.ymk.common.dto.VocQuestionDto;
import com.toyseven.ymk.common.model.entity.QVocQuestionEntity;
import com.toyseven.ymk.common.search.VocQuestionSearchCondition;
import com.toyseven.ymk.common.util.ToysevenCommonUtil;

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
					categoryIdEq(condition.getCategoryId()),
					activeEq(condition.getActive()),
					
					updatedAtGoe(condition.getStartAt()),
					updatedAtLt(condition.getEndAt())
				)
    			.offset(pageable.getOffset())
    			.limit(pageable.getPageSize())
    			.orderBy(orders.stream().toArray(OrderSpecifier[]::new))
//                .orderBy(question.updatedAt.desc())
    			.fetchResults();
        
    	List<VocQuestionDto.Response> content = result.getResults();
    	long total = result.getTotal();
    	return new PageImpl<>(content, pageable, total);
    	
//    	return new PageImpl<>(content);
    }

	// BooleanExpression으로 해야 나중에 Composition이 가능
	private BooleanExpression categoryIdEq(final String categoryId) { 
        return ToysevenCommonUtil.hasText(categoryId) ? question.category.displayName.eq(categoryId) : null;
    }
	
	private BooleanExpression titleEq(final String title) { 
		return ToysevenCommonUtil.hasText(title) ? question.title.eq(title) : null;
	}
	
	private BooleanExpression usernameEq(final String username) { 
		return ToysevenCommonUtil.hasText(username) ? question.username.eq(username) : null;
	}
	
	private BooleanExpression emailEq(final String email) { 
		return ToysevenCommonUtil.hasText(email) ? question.email.eq(email) : null;
	}
	
	private BooleanExpression stationIdEq(final String stationId) { 
        return ToysevenCommonUtil.hasText(stationId) ? question.stationId.stationId.eq(stationId) : null;
    }
	
	private BooleanExpression activeEq(final Boolean active) { 
        return (active != null) ? question.active.eq(active) : null;
    }
	
	private BooleanExpression updatedAtGoe(final String startAt) { 
		return (ToysevenCommonUtil.hasText(startAt)) ? question.updatedAt.goe(stringToLocalDateTime(startAt, Constants.START_AT.getTitle())) : null;
	}
	
	private BooleanExpression updatedAtLt(final String endAt) {
		return (ToysevenCommonUtil.hasText(endAt)) ? question.updatedAt.lt(stringToLocalDateTime(endAt, Constants.END_AT.getTitle())) : null;
	}
	
	private LocalDateTime stringToLocalDateTime(String str, String type) {
		
		LocalDate date = LocalDate.parse(str);
		if(type.equals(Constants.START_AT.getTitle()))
			return date.atStartOfDay();
		return date.atTime(LocalTime.MAX);
	}
	
	private List<OrderSpecifier<?>> getAllOrderSpecifiers() {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        OrderSpecifier<?> updatedAt = new QuerydslOrder(Order.DESC, question, "updatedAt").getSortedColumn();
        orders.add(updatedAt);
        return orders;
    }
	

}
