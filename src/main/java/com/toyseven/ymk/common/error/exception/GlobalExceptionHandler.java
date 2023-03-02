package com.toyseven.ymk.common.error.exception;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("handleMethodArgumentNotValidException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.REQUEST_BINDING_ERROR);
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        logger.error("handleBindException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.MODEL_BINDING_ERROR);
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error("handleMethodArgumentTypeMismatchException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.TYPE_BINDING_ERROR);
    }

	
    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("handleHttpRequestMethodNotSupportedException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 로직 수행 중 예외가 발생 한 경우
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        logger.error("handleBusinessException", e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
    
    /**
     * UserDetails 객체의 isEnabled() 메소드의 리턴값이 false
     * 	비활성화 된 계정 일 경우
     */
    @ExceptionHandler(DisabledException.class)
    protected ResponseEntity<ErrorResponse> handleDisabledException(DisabledException e) {
        logger.error("handleDisabledException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.DISABLED_USER);
    }
    
    /**
     * JWT Token 취득 시 암호가 일치하지 않은 경우
     */
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        logger.error("handleBadCredentialsException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.BAD_CREDENTIAL);
    }

    /**
     * JWT Token 취득 시 존재하지 않는 유저인 경우
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        logger.error("handleUsernameNotFoundException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.USER_NAME_NOT_FOUND);
    }

    
    /**
     * 해당 데이터가 존재하지 않는 경우
     */
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
    	logger.error("handleNoSuchElementException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.NO_SUCH_ELEMENT);
    }
    
    /**
     * 데이터 입력 시 Raw 가 정상적이지 않은 경우
     * 	ex) 게시글 답변 입력 시 foreign key 인 question id 에 해당하는 데이터가 존재하지 않는 경우 
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    	logger.error("handleDataIntegrityViolationException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.DATA_INTEGRITY_VIOLATION);
    }
    
    /**
     * 필수 Parameter 가 존재하지 않는 경우
     * 	ex) 특정 station 조회 시 필수 파마리터인 name 이 존재하지 않는 경우 
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintRequestParameterException(MissingServletRequestParameterException e) {
    	logger.error("handleMissingServletRequestParameterException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.MISSING_SERVLET_REQUEST_PARAMETER);
    }
    
    /**
     * 필수 Parameter 값이 없는 경우
     * 	ex) 특정 station 조회 시 필수 파마리터인 name 의 value 가 존재하지 않는 경우 
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
    	logger.error("handleConstraintViolationException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.MISSING_SERVLET_REQUEST_PARAMETER);
    }
    
    
    
    /**
     * Entity 와 Dto 간 변환이 실패한 경우
     * 	ex) entity 와 dto 사이 필드간 차이가 있는 경우
     */
    @ExceptionHandler(UnrecognizedPropertyException.class)
    protected ResponseEntity<ErrorResponse> handleUnrecognizedPropertyException(UnrecognizedPropertyException e) {
        logger.error("handleUnrecognizedPropertyException", e);
        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * JWT 토큰이 만료 된 경우
     */
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException e) {
    	logger.error("handleExpiredJwtException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.TOKEN_EXPIRED);
    }
    
    
    /**
     * JWT 토큰의 구성이 올바르지 않을 경우
     */
    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException e) {
    	logger.error("handleMalformedJwtException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    }
    
    /**
     * 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT 일 경우
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    protected ResponseEntity<ErrorResponse> handleUnsupportedJwtException(UnsupportedJwtException e) {
    	logger.error("handleUnsupportedJwtException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    }
    
    /**
     * JWT의 기존 서명을 확인하지 못했을 경우
     */
    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ErrorResponse> handleSignatureException(SignatureException e) {
    	logger.error("handleSignatureException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    }
    
    /**
     * Message 내용이 org.hibernate.TransientPropertyValueException 일 경우
     * 		영속성때문에 발생하는 오류
     * 		FK로 쓰는 객체가 존재하지 않을때 발생
     * 		@OneToMany, @ManyToOne 등 사용시 발생
     * 		(JPA Save 등)
     */
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    protected ResponseEntity<ErrorResponse> handleTransientPropertyValueException(InvalidDataAccessApiUsageException e) {
    	logger.error("handleInvalidDataAccessApiUsageException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.INVALID_DATA_ACCESS_API_USAGE);
    }
    
    /**
     * 형변환 시 객체 타입 변환이 적절하지 않을 때 발생
     */
    @ExceptionHandler(ClassCastException.class)
    protected ResponseEntity<ErrorResponse> handleClassCastException(ClassCastException e) {
    	logger.error("handleClassCastException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.CLASS_CAST_ERROR);
    }
    
    
    /**
     * Request 요청 시 데이터가 잘못 된 경우
     * 	ex) Request body 값의 타입이 Integer 로 넘어와야 하지만 String 으로 넘어 올 때
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    	logger.error("handleHttpMessageNotReadableException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.HTTP_MESSAGE_NOT_READABLE);
    }
    
    /**
     * Webclient 가 써드파티에 Request 를 실패 한 경우
     * 	ex) 써드파티 서버가 죽어있다던지, 써드파티의 주소가 잘못되어 있다던지 등
     */
    @ExceptionHandler(WebClientRequestException.class)
    protected ResponseEntity<ErrorResponse> handleWebClientRequestException(WebClientRequestException e) {
    	logger.error("handleWebClientRequestException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.WEBCLIENT_REQUEST_ERROR);
    }
    
    /**
     * Date 파싱에 실패 한 경우
     * 	ex) yyyyMMdd 형식으로 요청이 와야하지만 다른 형식으로 왔을 때
     */
    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<ErrorResponse> handleDateTimeParseExceptionException(DateTimeParseException e) {
    	logger.error("handleDateTimeParseExceptionException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.DATE_TIME_PARSING_ERROR);
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    	logger.error("handleException", e);
    	return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
