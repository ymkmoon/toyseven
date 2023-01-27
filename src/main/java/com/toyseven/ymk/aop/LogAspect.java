package com.toyseven.ymk.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.toyseven.ymk.common.util.DataParsingUtil;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
	
    Logger logger =  LoggerFactory.getLogger(LogAspect.class);
    
    // 특정 패키지에서 로그 출력
//    @Around("execution(* ~~~.~~~.~~~.~~~..*Service.*(..))")
    @Around("execution(* com.toyseven.ymk..*Service.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
    	return around(pjp);
    }
    // 특정 메소드에서 로그 출력
    @Around("execution(* com.toyseven.ymk.voc.VocController.saveVocAnswer(..))")
    public Object loggingWhenSaveVocAnswer(ProceedingJoinPoint pjp) throws Throwable {
    	// HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	logger.info("Answered by {}", auth.getName());
    	return pjp.proceed();
    }
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping() {
    	logger.info("Pointcut GetMapping(): begin");
    	logger.info("Pointcut GetMapping(): end");
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping() {
		logger.info("Pointcut PostMapping(): begin");
		logger.info("Pointcut PostMapping(): end");
	}
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMapping() {
		logger.info("Pointcut RequestMapping(): begin");
		logger.info("Pointcut RequestMapping(): end");
	}
    
	
	@Before("getMapping()") 
	public void beforeGetMethod(JoinPoint pjp) { 
		before(pjp);
	}
	@Before("postMapping()") 
	public void beforePostMethod(JoinPoint pjp) { 
		before(pjp);
	}
	@Before("requestMapping()") 
	public void beforeRequestMethod(JoinPoint pjp) { 
		before(pjp);
	}
	

	@Around("getMapping()")
	public Object aroundGet(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		Map<String, String[]> paramMap = request.getParameterMap();
		if (logger.isInfoEnabled() && !paramMap.isEmpty()) {
			logger.info("[ {} ]", DataParsingUtil.paramMapToString(paramMap));
		}
		return around(pjp);
	}
	
	@Around("postMapping()")
	public Object aroundPost(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		if (logger.isInfoEnabled()) {
			logger.info("[ {} ]", IOUtils.toString(request.getReader()));
		}
		return around(pjp);
	}
	@Around("requestMapping()")
	public Object aroundRequest(ProceedingJoinPoint pjp) throws Throwable {
		return around(pjp);
	}
	
	private final Object around(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("start @Arround - {} / {}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        Object result = pjp.proceed();
        logger.info("finished @Arround - {} / {}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        return result;
	}
	
	private final void before(JoinPoint pjp) {
		logger.info("start @Before - {} / {}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        logger.info("finished @Before - {} / {}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
	}
    
}
