package com.domain.system.services.interfaces;

import java.util.List;

import com.domain.system.models.postgresql.Usuario;

public interface IUserService {

	Usuario save(Usuario usuario);

	List<Usuario> findAll();

	void delete(Integer idUsuario);

	List<Usuario> findAll(Integer pagina);

	List<Usuario> findAll(Integer pagina, Integer cantidad);

	Usuario findById(Integer idUsuario);

	Usuario findByEmail(String email);

	List<Usuario> findByNombres(String nombres);

	List<Usuario> findByApellidoPaterno(String apellidoPaterno);

	List<Usuario> findByApellidoMaterno(String apellidoMaterno);

	List<Usuario> findByNombresOrApellidoPaternoOrApellidoMaterno(String string);

}
