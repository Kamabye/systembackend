package com.system.domain.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.system.domain.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		if (antPathMatcher.match("/apiv1/auth", request.getServletPath())) {
			
			//System.out.println("Solicitud por apiv1/auth");
			filterChain.doFilter(request, response);
			return;
		}
		
		//System.out.println("Solicitud que no es apiv1/auth");
		final String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			final String token = authHeader.substring(7);
			try {
				
				if (jwtService.isTokenValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
					
					final String username = jwtService.extractUsername(token);
					
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					  userDetails, null, userDetails.getAuthorities());
					
					// System.out.println(authToken.toString());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					// System.out.println(authToken.toString());
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
				}
				
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Error de autenticación: " + e);
				return;
			}
		}
		else {
			System.out.println("No hay Barer en la peticion");
		}
		
		filterChain.doFilter(request, response);
		
	}
	
}
