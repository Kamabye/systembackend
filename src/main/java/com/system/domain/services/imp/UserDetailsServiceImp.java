package com.system.domain.services.imp;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.domain.models.postgresql.Usuario;
import com.system.domain.repository.postgresql.UserRepository;
import com.system.domain.userdetails.UserDetailsImp;

@Service
//@Transactional
@Primary
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = userRepo.findByEmail(username)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con email: " + username));
		

		//System.out.println("UserDetailsServiceImp : " + usuario.toString());

		return new UserDetailsImp(usuario);
	}
}