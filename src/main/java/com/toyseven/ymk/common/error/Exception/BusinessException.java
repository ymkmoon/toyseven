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

}
