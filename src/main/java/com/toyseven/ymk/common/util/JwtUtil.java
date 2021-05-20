package com.toyseven.ymk.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.toyseven.ymk.common.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    public String getUsernameFromToken(String token) {
        return getCustomClaimFromToken(token, "username");
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getCustomClaimFromToken(String token, String claimName) {
        final Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get(claimName);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Constants.ACCESS_TOKEN_SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isAccessTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("username", userDetails.getUsername());

        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+Constants.ACCESS_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, Constants.ACCESS_TOKEN_SECRET)
                .compact();
    }

    public Boolean validateAccessToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isAccessTokenExpired(token);
    }
}
