package com.system.domain.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.system.domain.model.userdetails.UserDetailsImp;

@Service
public class AuthenticationByJwtService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	public String authenticate(String username, String password) {
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		if(authentication.isAuthenticated()) {
			
			UserDetailsImp usuario = (UserDetailsImp) userDetailsService.loadUserByUsername(username);
			
			//System.out.println(usuario.toString());
			
			return jwtService.generateToken(usuario);
			
		}
		//System.out.println("No autenticado");
		return null;
	}

}