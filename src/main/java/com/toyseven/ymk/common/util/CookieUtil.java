package com.toyseven.ymk.common.util;

import jakarta.servlet.http.Cookie;

import org.springframework.stereotype.Service;

import com.toyseven.ymk.jwt.JwtGroup;

@Service
public class CookieUtil {

    public Cookie createCookie(String key, String value) {
        Cookie token = new Cookie(key, value);

        token.setHttpOnly(true);
        token.setSecure(true);
        token.setMaxAge((int) JwtGroup.ACCESS_TOKEN.getValidity());
        token.setPath("/");

        return token;
    }
}
