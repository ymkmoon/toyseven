package com.toyseven.ymk.common.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.toyseven.ymk.common.ReadableRequestWrapper;

public class DefaultRequestFilter extends OncePerRequestFilter {
	
//	private static final List<String> EXCLUDE_URL =
//            Collections.unmodifiableList(
//                    Arrays.asList(
//                        "/voc/answer",
//                        "/actuator",
//                        "/actuator/health",
//                        "/voc/question", 
//                		"/cognito/payload/sub"
//                    ));

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		ReadableRequestWrapper wrapper = new ReadableRequestWrapper((HttpServletRequest)request);
		chain.doFilter(wrapper, response);
	}
	
//	@Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return !EXCLUDE_URL.stream().noneMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
//    }
}