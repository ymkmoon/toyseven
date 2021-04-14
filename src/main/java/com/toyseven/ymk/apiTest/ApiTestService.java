package com.toyseven.ymk.apiTest;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ApiTestService {
	List<?> apiTest();
}
