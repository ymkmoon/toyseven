package com.toyseven.ymk.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.toyseven.ymk.common.Constants;
import com.toyseven.ymk.common.dto.TokenDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.exception.BusinessException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JwtUtil {

	public String getUsernameFromAccessToken(String token) {
		return getCustomClaimFromAccessToken(token, "username");
	}
	
    public String getUsernameFromRefreshToken(String token) {
        return getCustomClaimFromRefreshToken(token, "username");
    }

    public Date getExpirationDateFromAccessToken(String token) {
        return getClaimFromAccessToken(token, Claims::getExpiration);
    }
    
    public Date getExpirationDateFromRefreshToken(String token) {
    	return getClaimFromRefreshToken(token, Claims::getExpiration);
    }

    
    public <T> T getClaimFromAccessToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromAccessToken(token);
        return claimsResolver.apply(claims);
    }
    
    public <T> T getClaimFromRefreshToken(String token, Function<Claims, T> claimsResolver) {
    	final Claims claims = getAllClaimsFromRefreshToken(token);
    	return claimsResolver.apply(claims);
    }
    

    public String getCustomClaimFromAccessToken(String token, String claimName) {
        final Claims claims = getAllClaimsFromAccessToken(token);
        return (String) claims.get(claimName);
    }
    
    public String getCustomClaimFromRefreshToken(String token, String claimName) {
    	final Claims claims = getAllClaimsFromRefreshToken(token);
    	return (String) claims.get(claimName);
    }
    

    private Claims getAllClaimsFromAccessToken(String token) {
        return Jwts.parser().setSigningKey(Constants.ACCESS_TOKEN_SECRET).parseClaimsJws(token).getBody();
    }
    
    private Claims getAllClaimsFromRefreshToken(String token) {
    	return Jwts.parser().setSigningKey(Constants.REFRESH_TOKEN_SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isAccessTokenExpired(String token) {
        final Date expiration = getExpirationDateFromAccessToken(token);
        return expiration.before(new Date());
    }
    
    private Boolean isRefreshTokenExpired(String token) {
    	final Date expiration = getExpirationDateFromRefreshToken(token);
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
                .signWith(SignatureAlgorithm.HS512, Constants.ACCESS_TOKEN_SECRET)
                .compact();
    }
    
    private String doGenerateRefreshToken(Map<String, Object> claims) {
    	return Jwts.builder()
    			.setClaims(claims)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis()+Constants.REFRESH_TOKEN_VALIDITY))
    			.signWith(SignatureAlgorithm.HS512, Constants.REFRESH_TOKEN_SECRET)
    			.compact();
    }

    public Boolean validateAccessToken(String accessToken, UserDetails userDetails) {
        final String username = getUsernameFromAccessToken(accessToken);
        return (username.equals(userDetails.getUsername())) && !isAccessTokenExpired(accessToken);
    }
    
    public String validateRefreshToken(String refreshToken){
    	final Claims claims = getAllClaimsFromRefreshToken(refreshToken);
        if(Boolean.FALSE.equals(isRefreshTokenExpired(refreshToken))) {
    		return doGenerateAccessToken(claims);
    	}
    	throw new BusinessException(ErrorCode.TOKEN_EXPIRED.getDetail(), ErrorCode.TOKEN_EXPIRED);
    }
    
}
