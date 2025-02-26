package com.system.domain.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class XFrameFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	  throws ServletException, IOException {
		
		//System.out.println("XFrameFilter");
		response.setHeader("X-FRAME-OPTIONS", "ALLOW-FROM http://localhost:4200");
		response.setHeader("X-FRAME-OPTIONS", "ALLOW-FROM http://localhost:8081");
		response.setHeader("X-FRAME-OPTIONS", "ALLOW-FROM https://system-i73z.onrender.com");
		response.setHeader("X-FRAME-OPTIONS", "ALLOW-FROM https://opticalemus.onrender.com");
		
		filterChain.doFilter(request, response);
		
	}
	
}
