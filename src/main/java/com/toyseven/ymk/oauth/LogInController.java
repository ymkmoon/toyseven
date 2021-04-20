package com.toyseven.ymk.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogInController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInController.class);

    @RequestMapping(value = "/loginForm")
    public ModelAndView loginForm(ModelAndView mv) {
        LOGGER.info("---------------------------------------");
        LOGGER.info("LogInController.java _ loginForm _ START");
        
        mv.setViewName("loginForm");
        return mv;
    }
    
    @RequestMapping(value = "/loginFail")
    public ModelAndView loginFail(ModelAndView mv) {
    	LOGGER.info("---------------------------------------");
    	LOGGER.info("LogInController.java _ loginFail _ START");
    	mv.setViewName("exception/fail");
    	
    	return mv;
    }
}
