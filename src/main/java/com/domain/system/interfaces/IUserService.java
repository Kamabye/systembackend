package com.domain.system.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.domain.system.models.postgresql.Usuario;

public interface IUserService {
	
	//Metodos que utilizan Derived Query Methods del repositorio. 
	//Los métodos de consulta derivados son aquellos cuyas consultas se generan automáticamente a partir de los nombres de los métodos.

	Usuario guardar(Usuario usuario);
	
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
	//JPQL es un lenguaje de consulta orientado a objetos similar a SQL, pero opera sobre entidades JPA en lugar de tablas de base de datos. 
	
	
	
	//Métodos que utilizan SQL Nativo
	//Las consultas SQL nativas son consultas escritas directamente en SQL. Estas se ejecutan directamente sobre la base de datos subyacente y son específicas del proveedor de la base de datos.

}
