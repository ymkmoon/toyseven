package com.toyseven.ymk.common.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
public class QuerydslConfig {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Bean 
	JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}

}
