package com.system.domain.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.system.domain.services.JwtService;

import io.jsonwebtoken.security.SignatureException;
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

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			final String token = authHeader.substring(7);
			try {
				final String username = jwtService.extractUsername(token);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					// UserDetails userDetails =
					// this.userDetailsService.loadUserByUsername(userEmail);
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					if (jwtService.isTokenValid(token, userDetails)) {

						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						// System.out.println(authToken.toString());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						// System.out.println(authToken.toString());
						SecurityContextHolder.getContext().setAuthentication(authToken);

					}
				}
			} catch (SignatureException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Error de autenticación: Firma JWT no válida");
				return;
			}
		}

		filterChain.doFilter(request, response);

	}

}
