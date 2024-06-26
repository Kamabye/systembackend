package com.herokuapp.kamabye.interfaces;

import java.sql.Blob;
import java.util.List;

import org.springframework.data.domain.Page;

import com.herokuapp.kamabye.interfaces.DTOProyecciones.IUsuarioDTO;
import com.herokuapp.kamabye.models.dto.UsuarioDTO;
import com.herokuapp.kamabye.models.postgresql.Usuario;

public interface IUserService {

	// Metodos que utilizan Derived Query Methods del repositorio.
	// Los métodos de consulta derivados son aquellos cuyas consultas se generan
	// automáticamente a partir de los nombres de los métodos.

	Long countByIdUsuario(Long idUsuario);

	Usuario save(Usuario usuario);

	UsuarioDTO updateDTO(Usuario usuario);

	Usuario update(Usuario usuario);

	UsuarioDTO guardarDTO(Usuario usuario);
	
	Usuario uploadImage(Long IdUsuario, Blob image);

	List<Usuario> saveAll(List<Usuario> usuarios);

	List<Usuario> findAll();

	Page<Usuario> findAll(Integer page, Integer size);

	List<Usuario> findByExample(Usuario usuario);

	Page<Usuario> findByExampleWithPage(Usuario usuario, Integer page, Integer size);

	void delete(Long idUsuario);

	Usuario deleteReturn(Long idUsuario);

	Usuario findByIdUsuario(Long idUsuario);

	Usuario findByEmail(String email);

	List<Usuario> findByNombres(String nombres);

	List<Usuario> findByApellidoPaterno(String apellidoPaterno);

	List<Usuario> findByApellidoMaterno(String apellidoMaterno);

	List<Usuario> findByNombresOrApellidoPaternoOrApellidoMaterno(String string);

	// Metodos que utilizan JQPL del repositorio
	// JPQL es un lenguaje de consulta orientado a objetos similar a SQL, pero opera
	// sobre entidades JPA en lugar de tablas de base de datos.

	List<IUsuarioDTO> jpqlfindAllUsuariosIDTO();

	Page<IUsuarioDTO> jpqlfindAllUsuariosIDTOPageable(Integer pageNumber, Integer pageSize);

	Page<UsuarioDTO> jpqlfindAllUsuariosDTOPageable(Integer pageNumber, Integer pageSize);

	// Métodos que utilizan SQL Nativo
	// Las consultas SQL nativas son consultas escritas directamente en SQL. Estas
	// se ejecutan directamente sobre la base de datos subyacente y son específicas
	// del proveedor de la base de datos.

	Page<IUsuarioDTO> sqlfindAllUsuariosIDTOPageable(int pageNumber, int pageSize);

	// Otros metodos
	Page<Usuario> obtenerSiguientePagina(Page<Usuario> paginaActual);

	Page<Usuario> obtenerAnteriorPagina(Page<Usuario> paginaActual);

}
