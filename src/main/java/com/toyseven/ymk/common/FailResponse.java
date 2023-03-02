package com.toyseven.ymk.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.context.BeanConstructor;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.ErrorResponse;

public class FailResponse {
	
	private final ObjectMapper objectMapper;
	
	private final HttpServletResponse response;
	private final ErrorCode errorCode;
	
	public FailResponse(HttpServletResponse response, ErrorCode errorCode) {
		this.response = response;
		this.errorCode = errorCode;
		this.objectMapper = (ObjectMapper)new BeanConstructor("objectMapper").getBean();
	}
	
	public void writer() throws IOException {
		ErrorResponse fail = ErrorResponse.toBuilder(errorCode);
		response.setStatus(errorCode.getHttpStatus().value());
	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    String json = objectMapper.writeValueAsString(fail);
	    PrintWriter writer = response.getWriter();
	    writer.write(json);
	    writer.flush();
	}

}
