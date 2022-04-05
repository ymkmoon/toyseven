package com.toyseven.ymk.common.util;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Service;

@Service
public class CookieUtil {

    public Cookie createCookie(String key, String value) {
        Cookie token = new Cookie(key, value);

        token.setHttpOnly(true);
        token.setSecure(true);
        token.setMaxAge((int) Constants.ACCESS_TOKEN_VALIDITY);
        token.setPath("/");

        return token;
    }
}
