package com.toyseven.ymk.cognito;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.common.dto.CognitoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("cognito")
public class CognitoController {
	
	private final CognitoService cognitoService;

	@GetMapping("/payload/sub")
	public ResponseEntity<String> message() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return new ResponseEntity<>(auth.getName(), HttpStatus.OK);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<CognitoDto.RefreshResponse> refresh(@RequestBody @Valid CognitoDto.RefreshRequest request) {
		CognitoDto.RefreshResponse response = cognitoService.refreshAccessToken(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
