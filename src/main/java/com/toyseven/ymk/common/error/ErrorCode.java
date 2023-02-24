package com.toyseven.ymk.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "비정상적인 접근 입니다."),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "Entity Not Found"),
    DATA_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST, "요청 데이터가 정상적이지 않습니다."),
    MISSING_SERVLET_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 파라미터가 존재하지 않습니다."),
    MODEL_BINDING_ERROR(HttpStatus.BAD_REQUEST, "요청 데이터가 정상적이지 않습니다."),
    REQUEST_BINDING_ERROR(HttpStatus.BAD_REQUEST, "요청 데이터가 정상적이지 않습니다."),
    TYPE_BINDING_ERROR(HttpStatus.BAD_REQUEST, "Parameter 값이 정상적이지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "요청 데이터가 정상적이지 않습니다."),
    INVALID_DATA_ACCESS_API_USAGE(HttpStatus.BAD_REQUEST, "요청 데이터가 정상적이지 않습니다."),
    
    STATION_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 Station 조회가 불가능 합니다."),
    CATEGORY_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 Category 조회가 불가능 합니다."),
    QUESTION_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 Question 조회가 불가능 합니다."),
    ANSWER_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 Answer 조회가 불가능 합니다."),
    ADMIN_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 Admin 조회가 불가능 합니다."),
    
    FAIL_COGNITO_REFRESH_ACCESSTOKEN(HttpStatus.BAD_REQUEST, "Cognito 토큰 갱신 실패"),

    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "토큰 값을 다시 한번 확인해주세요."),
    FAIL_AUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 실패"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료 된 토큰 입니다."),
    DISABLED_USER(HttpStatus.UNAUTHORIZED, "비활성화 된 계정 입니다."),
    USER_NAME_NOT_FOUND(HttpStatus.UNAUTHORIZED, "사용자를 찾을 수 없습니다."),
    BAD_CREDENTIAL(HttpStatus.UNAUTHORIZED, "암호가 일치하지 않습니다."),
    TOKEN_IS_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 토큰 입니다."),
    IS_NOT_WRITER(HttpStatus.UNAUTHORIZED, "작성자만 수정 가능합니다."),
    
    FAIL_COGNITO_GET_USERINFO(HttpStatus.UNAUTHORIZED, "Cognito 유저 정보 획득 실패"),
    
    // 403
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "허가되지 않은 권한입니다."),
    
    // 404
    NO_SUCH_ELEMENT(HttpStatus.NOT_FOUND, "데이터가 존재하지 않습니다."),
    
    // 405
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않은 HTTP method 입니다."),
    
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),
    CLASS_CAST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의 하십시오."),
    STATION_NAME_ENCODING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Station Name 의 값이 잘못되었습니다."),
	WEBCLIENT_REQUEST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Third Party 요청에 실패했습니다. 관리자에게 문의해주세요."),
	DATE_TIME_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Date 타입의 값이 잘못된 형식입니다. yyyy-MM-dd 형식에 맞춰주세요."),
	READ_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 파일을 읽어들이는것에 실패했습니다. "),
	JSON_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 데이터를 처리(구문 분석, 생성)할 때 오류가 발생했습니다.")
	;
	
	
	private final HttpStatus httpStatus;
    private final String detail;
}
