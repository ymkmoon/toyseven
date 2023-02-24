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
import com.toyseven.ymk.jwt.JwtGroup;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JwtUtil {

	public String getUsernameFromToken(String token, String tokenType) {
		return getCustomClaimFromToken(token, Constants.USERNAME.getTitle(), tokenType);
	}
	
	public Date getExpirationDateFromToken(String token, String tokenType) {
        return getClaimFromToken(token, Claims::getExpiration, tokenType);
    }
	
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String tokenType) {
        final Claims claims = getAllClaimsFromToken(token, tokenType);
        return claimsResolver.apply(claims);
    }
    
    public String getCustomClaimFromToken(String token, String claimName, String tokenType) {
        final Claims claims = getAllClaimsFromToken(token, tokenType);
        return (String) claims.get(claimName);
    }
    
    private Claims getAllClaimsFromToken(String token, String tokenType) {
    	JwtGroup tokenConfig = JwtGroup.tokenInformation(tokenType);
        return Jwts.parser().setSigningKey(tokenConfig.getSecretKey()).parseClaimsJws(token).getBody();
    }
    
    private Boolean isTokenExpired(String token, String tokenType) {
        final Date expiration = getExpirationDateFromToken(token, tokenType);
        return expiration.before(new Date());
    }

    public TokenDto.Request generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(Constants.USERNAME.getTitle(), userDetails.getUsername());
        
        String accessToken = doGenerateToken(claims, Constants.ACCESS_TOKEN.getTitle());
        String refreshToken = doGenerateToken(claims, Constants.REFRESH_TOKEN.getTitle());
        
        return TokenDto.Request.builder()
		        .accessToken(accessToken)
		        .refreshToken(refreshToken)
		        .build();
        
    }
    
    private String doGenerateToken(Map<String, Object> claims, String tokenType) {
    	JwtGroup tokenConfig = JwtGroup.tokenInformation(tokenType);
    	
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+tokenConfig.getValidity()))
                .signWith(SignatureAlgorithm.HS512, tokenConfig.getSecretKey())
                .compact();
    }
    

    public Boolean validateAccessToken(String accessToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(accessToken, Constants.ACCESS_TOKEN.getTitle());
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(accessToken, Constants.ACCESS_TOKEN.getTitle());
    }
    
    public String validateRefreshToken(String refreshToken){
    	final Claims claims = getAllClaimsFromToken(refreshToken, Constants.REFRESH_TOKEN.getTitle());
        if(Boolean.FALSE.equals(isTokenExpired(refreshToken, Constants.REFRESH_TOKEN.getTitle()))) {
//    		return doGenerateAccessToken(claims);
        	return doGenerateToken(claims, Constants.ACCESS_TOKEN.getTitle());
    	}
    	throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
    }
    
}
