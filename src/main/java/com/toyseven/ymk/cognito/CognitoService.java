package com.toyseven.ymk.cognito;

import jakarta.transaction.Transactional;

import com.toyseven.ymk.common.dto.CognitoDto;

@Transactional
public interface CognitoService {
	CognitoDto.RefreshResponse refreshAccessToken(CognitoDto.RefreshRequest request);
}
