package com.domain.system.services.imp;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.domain.system.models.postgresql.Usuario;
import com.domain.system.repository.postgresql.UserRepository;
import com.domain.system.userdetails.UserDetailsImp;

@Service
@Primary
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = userRepo.findByEmail(username)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con email: " + username));

		System.out.println("UserDetailsServiceImp : " + usuario.toString());

		return new UserDetailsImp(usuario);
	}
}