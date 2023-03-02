package com.toyseven.ymk.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.toyseven.ymk.common.Constants;
import com.toyseven.ymk.common.FailResponse;
import com.toyseven.ymk.common.ReadableRequestWrapper;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.util.JwtUtil;
import com.toyseven.ymk.jwt.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;

//@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
    
    private static final List<String> INCLUDE_URL =
            Collections.unmodifiableList(
                    Arrays.asList(
                        "/voc/answer",
                        "/actuator",
                        "/actuator/health"
                    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
    	
        String accessToken = getAccessTokenFromRequestHeader(request);
        
    	try {
    		String username = JwtUtil.getUsernameFromToken(accessToken, Constants.ACCESS_TOKEN.getTitle());
    		UserDetails userDetails = this.jwtService.loadUserByUsername(username);
    		if (Boolean.TRUE.equals(JwtUtil.validateAccessToken(accessToken, userDetails))) {
    			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
    					userDetails, null, userDetails.getAuthorities());
    			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    		}
    	} catch (IllegalArgumentException | AccessDeniedException | MalformedJwtException | SignatureException e) {
    		logger.error("Unable to get JWT Token", e);
    		new FailResponse(response, ErrorCode.FAIL_AUTHORIZED).writer();
    		return;
    	} catch (ExpiredJwtException e) {
    		logger.error("JWT Token has expired", e);
    		new FailResponse(response, ErrorCode.TOKEN_EXPIRED).writer();
    		return;
    	} catch (Exception e) {
    		logger.error("Unable to get JWT Token", e);
    		new FailResponse(response, ErrorCode.FAIL_AUTHORIZED).writer();
    		return;
    	}
        
        ReadableRequestWrapper wrapper = new ReadableRequestWrapper(request);
        chain.doFilter(wrapper, response);
    }
    
    /**
     * return shouldNotFilter
     * 	true : not execute doFilterInternal
     * 	false : execute doFilterInternal
     * 
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return INCLUDE_URL.stream().noneMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }

    private String getAccessTokenFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}