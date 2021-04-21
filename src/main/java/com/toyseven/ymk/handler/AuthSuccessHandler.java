package com.toyseven.ymk.handler;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {
	
	public final Integer SESSION_TIMEOUT_IN_SECONDS = 60 * 1;
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT_IN_SECONDS);
		
        HttpSession session = request.getSession();
//        session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");
//        response.sendRedirect("/");
		
        
		Enumeration<?> e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String sessionAttribute = (String) e.nextElement();
			System.out.println("session.getAttribute("+ sessionAttribute +"): " + session.getAttribute(sessionAttribute));
		}
		
		System.out.println("authentication.getName() : "+authentication.getName());
    }
}
