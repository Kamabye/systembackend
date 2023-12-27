package com.domain.system.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.domain.system.models.postgresql.Usuario;
import com.domain.system.userdetails.UserDetailsImp;

@Service
@Primary
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserServiceImpJpa userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//System.out.println("UserDetailsServiceImp : ");
		Usuario usuario = userService.findByEmail(username);
		//System.out.println("UserDetailsServiceImp : Se buscó al usuario");
		if (usuario == null) {
			//System.out.println("El usuario no se encontró");
			throw new UsernameNotFoundException("Usuario no encontrado: " + username);
		}
		//System.out.println("Éxito Usuario encontrado");
		return new UserDetailsImp(usuario);
	}

}
