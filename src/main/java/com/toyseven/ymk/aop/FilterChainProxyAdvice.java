package com.toyseven.ymk.aop;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;

import com.toyseven.ymk.common.FailResponse;
import com.toyseven.ymk.common.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class FilterChainProxyAdvice {
	
	/**
	 * 요청 시 URI 에 슬래시(/)가 두개 존재 할 경우 실행되는 aop
	 */
	@Around("execution(public void org.springframework.security.web.FilterChainProxy.doFilter(..))")
	public void handleRequestRejectedException (ProceedingJoinPoint pjp) throws Throwable {
	    try {
	        pjp.proceed();
	    } catch (RequestRejectedException exception) {
	        HttpServletResponse response = (HttpServletResponse) pjp.getArgs()[1];
		    new FailResponse(response, ErrorCode.BAD_REQUEST).writer();
	    }
	}
}
