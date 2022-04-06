package com.toyseven.ymk.admin;

import javax.servlet.http.HttpServletResponse;

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
public class AdminController {

    private final AdminService adminService;
    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final CookieUtil cookieUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<AdminDto.Response> login(@RequestBody AdminDto.Request adminRequest, HttpServletResponse response) {
        UserDetails userDetails = adminService.loadUserByUsername(adminRequest.getUsername());
        TokenDto.Response token = JwtUtil.generateToken(userDetails);
        
        adminService.saveRefreshToken(token);

        authenticate(adminRequest.getUsername(), adminRequest.getPassword());

//        Cookie accessToken = cookieUtil.createCookie(Constants.ACCESS_TOKEN, token);
//        response.addCookie(accessToken);
        
        AdminDto.Response adminResponse = AdminDto.Response.builder()
        									.accessToken(token.getAccessToken())
        									.refreshToken(token.getRefreshToken())
        									.build();
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }
    
    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenDto.RefreshRequest refreshRequest) {
    	boolean registRefreshToken = adminService.validateRegistRefreshToken(refreshRequest);;
    	if(!registRefreshToken) {
    		return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    	}
    	
    	String accessToken = JwtUtil.validateRefreshToken(refreshRequest.getRefreshToken());
    	AdminDto.Response adminResponse = AdminDto.Response.builder()
				.accessToken(accessToken)
				.refreshToken(refreshRequest.getRefreshToken())
				.build();
    	return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    	
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
