package com.toyseven.ymk.cognito;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.toyseven.ymk.common.ResponseEntityComponent;
import com.toyseven.ymk.common.dto.CognitoDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CognitoServiceImpl implements CognitoService {
	
	private final ResponseEntityComponent responseEntityComponent;
	
	@Value("${aws.cognito.domaim}")
	private String ISSUER_URI;
	
	@Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
	private String CLIENT_ID;
	
	private static final String REFRESH_TOKEN = "refresh_token";
	
	@Override
	public CognitoDto.RefreshResponse refreshAccessToken(CognitoDto.RefreshRequest request) {
		
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(ISSUER_URI);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
		WebClient wc = WebClient.builder().uriBuilderFactory(factory)
				.baseUrl(ISSUER_URI).build();
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", REFRESH_TOKEN);
		params.add("client_id", CLIENT_ID);
		params.add(REFRESH_TOKEN, request.getRefreshToken());
		
		ResponseEntity<JSONObject> cognitoResponse = responseEntityComponent.cognitoRefreshToken(wc, params);
		
		if(cognitoResponse.hasBody()) {
			String accessToken = cognitoResponse.getBody().get("access_token").toString();
			return CognitoDto.RefreshResponse.builder()
					.accessToken(accessToken)
					.refreshToken(request.getRefreshToken())
					.build();
		}
		
		throw new BusinessException(ErrorCode.FAIL_COGNITO_GET_USERINFO.getDetail(), ErrorCode.FAIL_COGNITO_GET_USERINFO);
		
	}

}
