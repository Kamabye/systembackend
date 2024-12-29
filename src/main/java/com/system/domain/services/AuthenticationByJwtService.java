package com.system.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationByJwtService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	public String authenticate(String username, String password) {
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		if(authentication.isAuthenticated()) {
			System.out.println("JwtService.authenticate : " + authentication.toString());
			return jwtService.generateToken((UserDetails) authentication.getPrincipal());
		}
		System.out.println("No autenticado");
		return null;
	}
}