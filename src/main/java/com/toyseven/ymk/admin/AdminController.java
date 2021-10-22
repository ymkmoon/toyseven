package com.toyseven.ymk.admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.admin.dto.request.LoginRequest;
import com.toyseven.ymk.admin.dto.response.LoginResponse;
import com.toyseven.ymk.common.Constants;
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
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IllegalArgumentException {
        UserDetails userDetails = adminService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        LoginResponse loginResponse = new LoginResponse();

        authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        Cookie accessToken = cookieUtil.createCookie(Constants.ACCESS_TOKEN, token);

        response.addCookie(accessToken);
        loginResponse.setAccessToken(token);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws IllegalArgumentException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException exception) {
            throw new DisabledException("Disabled user", exception);
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Invalid credentials", exception);
        }
    }
}
