package com.toyseven.ymk.jwt;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.common.dto.AdminDto;
import com.toyseven.ymk.common.dto.TokenDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.ErrorResponse;
import com.toyseven.ymk.common.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class JwtController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDto.Response> login(@RequestBody AdminDto.Request adminRequest) {
        UserDetails userDetails = jwtService.loadUserByUsername(adminRequest.getUsername());
        TokenDto.Request token = JwtUtil.generateToken(userDetails);
        
        jwtService.saveRefreshToken(token);

        authenticate(adminRequest.getUsername(), adminRequest.getPassword());
        
        TokenDto.Response response = TokenDto.Response.builder()
        									.accessToken(token.getAccessToken())
        									.refreshToken(token.getRefreshToken())
        									.build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Valid TokenDto.RefreshRequest refreshRequest) {
    	boolean registRefreshToken = jwtService.validateRegistRefreshToken(refreshRequest);
    	if(!registRefreshToken) {
    		return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    	}
    	
    	String accessToken = JwtUtil.validateRefreshToken(refreshRequest.getRefreshToken());
    	TokenDto.Response adminResponse = TokenDto.Response.builder()
				.accessToken(accessToken)
				.refreshToken(refreshRequest.getRefreshToken())
				.build();
    	return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    	
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
