package com.toyseven.ymk.admin;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.toyseven.ymk.common.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
	

	private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        String accessToken = getAccessTokenFromRequestHeader(request);
        String username = null;
        
        if(accessToken != null) {
        	try {
        		username = jwtUtil.getUsernameFromToken(accessToken);
        	} catch (IllegalArgumentException | AccessDeniedException | MalformedJwtException | SignatureException e) {
        		logger.info("Unable to get JWT Token");
        		response.sendError(401, "Unable to get JWT Token");
        		return;
        	} catch (ExpiredJwtException e) {
        		logger.info("JWT Token has expired");
        		response.sendError(401, "JWT Token has expired");
        		return;
        	} catch (Exception e) {
        		logger.info("Unable to get JWT Token");
        		response.sendError(500, "Unable to get JWT Token");
        		return;
        	}
        	
        	if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        		
        		UserDetails userDetails = this.adminService.loadUserByUsername(username);
        		
        		if (Boolean.TRUE.equals(jwtUtil.validateAccessToken(accessToken, userDetails))) {
        			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        					userDetails, null, userDetails.getAuthorities());
        			usernamePasswordAuthenticationToken
        			.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        		}
        	}
        }
        chain.doFilter(request, response);
    }

    private String getAccessTokenFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}