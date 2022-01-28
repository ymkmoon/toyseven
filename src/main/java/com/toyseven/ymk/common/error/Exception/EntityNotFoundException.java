package com.toyseven.ymk.common.error.Exception;

import com.toyseven.ymk.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
