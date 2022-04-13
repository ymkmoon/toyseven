package com.toyseven.ymk.common.error.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.ErrorResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
    	ErrorResponse fail = ErrorResponse.builder()
				.status(ErrorCode.UNAUTHORIZED.getHttpStatus().value())
				.error(ErrorCode.UNAUTHORIZED.getHttpStatus().name())
                .code(ErrorCode.UNAUTHORIZED.name())
                .message(ErrorCode.UNAUTHORIZED.getDetail())
                .build();
		
	    response.setStatus(ErrorCode.UNAUTHORIZED.getHttpStatus().value());
	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    String json = objectMapper.writeValueAsString(fail);
	    PrintWriter writer = response.getWriter();
	    writer.write(json);
	    writer.flush();
    }
    
}
