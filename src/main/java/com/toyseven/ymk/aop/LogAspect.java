package com.toyseven.ymk.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
	
    Logger logger =  LoggerFactory.getLogger(LogAspect.class);
    
//    // 특정 패키지에서 로그 출력
//    @Around("execution(* ~~~.~~~.~~~.~~~..*Service.*(..))")
//    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
//        logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
//        Object result = pjp.proceed();
//        logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
//        return result;
//    }
    
    // 특정 메소드에서 로그 출력
    @Around("execution(* com.toyseven.ymk.voc.VocController.saveVocAnswer(..))")
    public Object logCongitoId(ProceedingJoinPoint pjp) throws Throwable {
    	// HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	logger.info("Answered by {}", auth.getName());
    	Object result = pjp.proceed();
    	return result;
    }
    
    
}
