package com.toyseven.ymk.apiTest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value = "/language", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView languageTest(ModelAndView mv,
			@RequestParam(value="language", required = true, defaultValue="ko") String language) {
		logger.info("this is languageTest method");
		
		mv.setViewName("test/test");
		return mv;
	}
	
}
