package com.domain.system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.domain.system.models.postgresql.Usuario;
import com.domain.system.services.interfaces.impdbjpa.UserServiceImpJpa;
import com.domain.system.userdetails.UserDetailsImp;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserServiceImpJpa userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = userService.findByEmail(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario no encontrado: " + username);
		}
		return new UserDetailsImp(usuario);
	}

}
