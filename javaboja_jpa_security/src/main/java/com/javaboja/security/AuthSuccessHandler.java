package com.javaboja.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/*@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
    	request.setAttribute("loginId",request.getParameter("userId"));
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("{\"success\": true}");
        response.getWriter().flush();
    }
 
}*/
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler{
	  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();;
	  
	  @Override
	  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	      Authentication authentication) throws IOException, ServletException {
	      redirectStrategy.sendRedirect(request, response, "/main");
	  }
	}