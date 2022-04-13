package com.toyseven.ymk.common.error.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.ErrorResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		ErrorResponse fail = ErrorResponse.builder()
				.status(ErrorCode.ACCESS_DENIED.getHttpStatus().value())
				.error(ErrorCode.ACCESS_DENIED.getHttpStatus().name())
                .code(ErrorCode.ACCESS_DENIED.name())
                .message(ErrorCode.ACCESS_DENIED.getDetail())
                .build();
		
	    response.setStatus(ErrorCode.ACCESS_DENIED.getHttpStatus().value());
	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    String json = objectMapper.writeValueAsString(fail);
	    PrintWriter writer = response.getWriter();
	    writer.write(json);
	    writer.flush();
		
	}
	
}
