package com.toyseven.ymk.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.toyseven.ymk.common.FailResponse;
import com.toyseven.ymk.common.ReadableRequestWrapper;
import com.toyseven.ymk.common.ResponseEntityComponent;
import com.toyseven.ymk.common.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuth2RequestFilter extends OncePerRequestFilter {
	
	private final ResponseEntityComponent responseEntityComponent;
	private final String ISSUER_URI;
    
//    private static final List<String> INCLUDE_URL =
//            Collections.unmodifiableList(
//                    Arrays.asList(
//                        "/voc/question", 
//                        "/cognito/payload/sub"
//                    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
    	
        String accessToken = getAccessTokenFromRequestHeader(request);
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(ISSUER_URI);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
		WebClient wc = WebClient.builder().uriBuilderFactory(factory)
				.baseUrl(ISSUER_URI).build();
		
		try {
			ResponseEntity<JSONObject> cognitoResponse = responseEntityComponent.cognitoGetUserInfo(wc, accessToken);
			String username = cognitoResponse.getBody().get("sub").toString();
			UserDetails userDetails = User.builder().username(username).password("").roles("USER").build();
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		} catch(Exception e) {
			new FailResponse(response, ErrorCode.FAIL_GET_COGNITO_USERINFO).writer();
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
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return INCLUDE_URL.stream().noneMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
//    }

    private String getAccessTokenFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}