package com.toyseven.ymk.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.toyseven.ymk.common.ReadableRequestWrapper;

@Component
public class DefaultRequestFilter implements Filter {
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ReadableRequestWrapper rereadableRequestWrapper = new ReadableRequestWrapper((HttpServletRequest)request);
        chain.doFilter(rereadableRequestWrapper, response);
    }
}