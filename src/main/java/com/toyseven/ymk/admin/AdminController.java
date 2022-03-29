package com.toyseven.ymk.admin;

import javax.servlet.http.Cookie;
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

import com.toyseven.ymk.common.Constants;
import com.toyseven.ymk.common.dto.admin.AdminRequest;
import com.toyseven.ymk.common.dto.admin.AdminResponse;
import com.toyseven.ymk.common.util.CookieUtil;
import com.toyseven.ymk.common.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AdminController {

    private final AdminService adminService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody AdminRequest adminRequest, HttpServletResponse response) {
        UserDetails userDetails = adminService.loadUserByUsername(adminRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        AdminResponse adminResponse = new AdminResponse();

        authenticate(adminRequest.getUsername(), adminRequest.getPassword());

        Cookie accessToken = cookieUtil.createCookie(Constants.ACCESS_TOKEN, token);

        response.addCookie(accessToken);
        adminResponse.setAccessToken(token);

        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
