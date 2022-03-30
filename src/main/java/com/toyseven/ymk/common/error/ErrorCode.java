package com.toyseven.ymk.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "비정상적인 접근 입니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value"),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value"),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "Entity Not Found"),
    DATA_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST, "데이터를 다시 한번 확인해주세요."),
    MISSING_SERVLET_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 파라미터가 존재하지 않습니다."),

    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unable to get JWT Token"),
    DISABLED_USER(HttpStatus.UNAUTHORIZED, "비활성화 된 계정 입니다."),
    USER_NAME_NOT_FOUND(HttpStatus.UNAUTHORIZED, "사용자를 찾을 수 없습니다."),
    BAD_CREDENTIAL(HttpStatus.UNAUTHORIZED, "암호가 일치하지 않습니다."),
    
    // 403
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 하기 위해서는 권한을 확인해주세요."),
    
    // 404
    NO_SUCH_ELEMENT(HttpStatus.NOT_FOUND, "No value present"),
    
    // 405
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않은 HTTP method 입니다."),
    
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),
    STATION_NAME_ENCODING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Station Name 의 값이 잘못되었습니다.");
	
	private final HttpStatus httpStatus;
    private final String detail;
}
