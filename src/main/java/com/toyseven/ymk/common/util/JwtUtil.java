package com.toyseven.ymk.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.toyseven.ymk.common.dto.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;

@UtilityClass
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
        return Jwts.parser().setSigningKey(Constants.TOKEN_SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public TokenDto.Request generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("username", userDetails.getUsername());

        String accessToken = doGenerateAccessToken(claims);
        String refreshToken = doGenerateRefreshToken(claims);
        
        return TokenDto.Request.builder()
		        .accessToken(accessToken)
		        .refreshToken(refreshToken)
		        .build();
        
    }

    private String doGenerateAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+Constants.ACCESS_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET)
                .compact();
    }
    
    private String doGenerateRefreshToken(Map<String, Object> claims) {
    	return Jwts.builder()
    			.setClaims(claims)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis()+Constants.REFRESH_TOKEN_VALIDITY))
    			.signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET)
    			.compact();
    }

    public Boolean validateAccessToken(String accessToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(accessToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(accessToken);
    }
    
    public String validateRefreshToken(String refreshToken){
    	final Claims claims = getAllClaimsFromToken(refreshToken);
        return isTokenExpired(refreshToken) ? null : doGenerateAccessToken(claims); 
    }
    
}
