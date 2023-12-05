package com.domain.system.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.domain.system.models.postgresql.Rol;
import com.domain.system.models.postgresql.Usuario;

public interface IUserService {
	
	//Metodos que utilizan Derived Query Methods del repositorio

	Usuario save(Usuario usuario);
	
	List<Usuario> saveAll(List<Usuario> usuarios);

	List<Usuario> findAll();
	
	Page<Usuario> findAllPage(Integer page, Integer size);
	
	List<Usuario> findByExample(Usuario usuario);
	
	Page<Usuario> findByExampleWithPage(Usuario usuario, Integer page, Integer size);

	void delete(Long idUsuario);

	Usuario findById(Long idUsuario);

	Usuario findByEmail(String email);

	List<Usuario> findByNombres(String nombres);

	List<Usuario> findByApellidoPaterno(String apellidoPaterno);

	List<Usuario> findByApellidoMaterno(String apellidoMaterno);

	List<Usuario> findByNombresOrApellidoPaternoOrApellidoMaterno(String string);

	Usuario deleteReturn(Long idUsuario);
	
	
	//Metodos que utilizan JQPL del repositorio
	
	
	
	
	//Métodos que utilizan SQL del repositorio

}
