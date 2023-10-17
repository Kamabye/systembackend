package com.domain.system.services.interfaces.impdbjpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.domain.system.models.postgresql.Usuario;
import com.domain.system.repository.postgresql.UserRepository;
import com.domain.system.services.interfaces.IUserService;

@Service
public class UserServiceJPA implements IUserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		return userRepository.findAll(Sort.by("nombres").ascending());
	}

	@Override
	public void delete(Integer idUsuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Usuario> findAll(Integer pagina) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll(Integer pagina, Integer cantidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findById(Integer idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findByEmail(String email) {
		Optional<Usuario> optional = userRepository.findByEmail(email);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Usuario> findByNombres(String nombres) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findByApellidoPaterno(String apellidoPaterno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findByApellidoMaterno(String apellidoMaterno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findByNombresOrApellidoPaternoOrApellidoMaterno(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
