package com.toyseven.ymk.common.error;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.common.FailResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String ERROR_PATH = "/error";
	 
    public String getErrorPath() {
        return ERROR_PATH;
    }
	
    @RequestMapping("/error")
    public void handleError(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        logger.error("status : {}", status);
        
        switch(status.toString()) {
        	case "404":
        		failResponse(response, ErrorCode.NOT_FOUND);
        		break;
        	default:
        		failResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
        		break;
        		        	
        }
    }
	
    private void failResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
    	new FailResponse(response, errorCode).writer();
    }
}
