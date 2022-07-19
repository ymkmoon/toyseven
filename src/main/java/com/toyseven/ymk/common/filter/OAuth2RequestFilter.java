package com.toyseven.ymk.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyseven.ymk.common.ResponseEntityComponent;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.ErrorResponse;
import com.toyseven.ymk.common.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2RequestFilter extends OncePerRequestFilter {
	
	private final ResponseEntityComponent responseEntityComponent;
	
	@Value("${aws.cognito.domaim}")
	private String ISSUER_URI;
	
    private final ObjectMapper objectMapper;
    
    private static final List<String> INCLUDE_URL =
            Collections.unmodifiableList(
                    Arrays.asList(
                        "/voc/question"
                    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
    	
        String accessToken = getAccessTokenFromRequestHeader(request);
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(ISSUER_URI);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
		WebClient wc = WebClient.builder().uriBuilderFactory(factory)
				.baseUrl(ISSUER_URI).build();
		
		try {
			responseEntityComponent.cognitoGetUserInfo(wc, accessToken);
		} catch(Exception e) {
			failResponse(response, ErrorCode.FAIL_COGNITO_GET_USERINFO);
			return;
		}
		
        chain.doFilter(request, response);
    }
    
    /**
     * return shouldNotFilter
     * 	true : not execute doFilterInternal
     * 	false : execute doFilterInternal
     * 
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return INCLUDE_URL.stream().noneMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }

    private String getAccessTokenFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        throw new BusinessException(ErrorCode.FAIL_AUTHORIZED.getDetail(), ErrorCode.FAIL_AUTHORIZED);
    }
    
    private void failResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
    	
//    	ErrorResponse fail = ErrorResponse.builder()
//				.status(errorCode.getHttpStatus().value())
//				.error(errorCode.getHttpStatus().name())
//                .code(errorCode.name())
//                .message(errorCode.getDetail())
//                .build();
    	
    	ErrorResponse fail = ErrorResponse.toBuilder(errorCode);
    	
		response.setStatus(errorCode.getHttpStatus().value());
	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    String json = objectMapper.writeValueAsString(fail);
	    PrintWriter writer = response.getWriter();
	    writer.write(json);
	    writer.flush();
    }
}