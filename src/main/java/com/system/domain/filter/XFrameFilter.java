package com.system.domain.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Este filtro se utiliza para registrar información sobre las solicitudes
 * entrantes, como la URL, el método HTTP, los encabezados y el cuerpo de la
 * solicitud.
 */
@Component
public class XFrameFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(XFrameFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	  throws ServletException, IOException {
		logger.info("Solicitud: {} {}", request.getMethod(), request.getRequestURI());
		
		// System.out.println("XFrameFilter");
		response.setHeader("X-FRAME-OPTIONS", "ALLOW-FROM http://localhost:4200");
		response.setHeader("X-FRAME-OPTIONS", "ALLOW-FROM http://localhost:8081");
		response.setHeader("X-FRAME-OPTIONS", "ALLOW-FROM https://system-i73z.onrender.com");
		response.setHeader("X-FRAME-OPTIONS", "ALLOW-FROM https://opticalemus.onrender.com");
		
		filterChain.doFilter(request, response);
		
	}
	
}
