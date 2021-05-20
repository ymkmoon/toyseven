package com.toyseven.ymk.langauge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LanguageController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/language", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView languageTest(ModelAndView mv,
			@RequestParam(value="language", required = true, defaultValue="ko") String language) {
		logger.info("this is languageTest method");
		
		mv.setViewName("test/test");
		return mv;
	}
	
}
