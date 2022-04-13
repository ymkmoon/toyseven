package com.toyseven.ymk.common.error.Exception;

import com.toyseven.ymk.common.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ErrorCode errorCode;
	
    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
