package com.toyseven.ymk.apiTest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired ApiTestService sampleService;

	@RequestMapping(value = "/apiTest", method = RequestMethod.GET)
	@ResponseBody
	public List<?> test() {
		logger.info("this is test method");
		return sampleService.apiTest();
	}
	
}
