package com.domain.system.repository.postgresql;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.domain.system.interfaces.proyecciones.UsuarioProyeccion;
import com.domain.system.models.dto.NombreDTO;
import com.domain.system.models.dto.UsuarioDTO;
import com.domain.system.models.postgresql.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {
	
	
	//Derived Query Methods

	List<Usuario> findByNombres(String nombres);

	List<Usuario> findByNombres(String nombres, Pageable pageable);

	List<Usuario> findByNombresOrderByNombresAsc(String nombres, Pageable pageable);

	List<Usuario> findByApellidoPaternoOrderByNombresAsc(String apellidoPaterno);

	List<Usuario> findByApellidoMaternoOrderByNombresAsc(String apellidoMaterno);

	Optional<Usuario> findByEmail(String email);

	List<Usuario> findByNombresContainingOrApellidoPaternoContainingOrApellidoMaternoContainingOrderByNombresAsc(
			String nombres, String apellidoPaterno, String apellidoMaterno);

	// Métodos @Query JPQL Java Persistence Query Language

	@Query("SELECT u FROM Usuario u")
	List<Usuario> queryObtenerUsuarios();
	
	@Query("SELECT u FROM Usuario u")	
	Page<Usuario> jpqlObtenerUsuarios(Pageable pageable);

	@Query("SELECT new com.domain.system.models.dto.UsuarioDTO(u.id, u.nombre) FROM Usuario u")
	List<UsuarioDTO> jpqlObtenerUsuariosDTO();

	@Query("SELECT new com.domain.system.models.dto.UsuarioDTO(u.nombre, u.email) FROM Usuario u WHERE u.nombre = :nombre")
	List<UsuarioDTO> jpqlBuscarUsuariosPorNombre(@Param("nombre") String nombre);

	@Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
	List<Usuario> jpqlBuscarPorNombre(@Param("nombre") String nombre);

	@Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombre = :rolNombre")
	List<Usuario> jpqlBuscarPorRol(@Param("rolNombre") String rolNombre);

	@Query("SELECT u FROM Usuario u ORDER BY u.nombre DESC")
	List<Usuario> jpqlObtenerUsuariosOrdenados();

	@Query("SELECT u FROM Usuario u WHERE (:nombre IS NULL OR u.nombre = :nombre) AND (:email IS NULL OR u.email = :email)")
	List<Usuario> jpqlBuscarUsuariosPorNombreYEmail(@Param("nombre") String nombre, @Param("email") String email);

	@Query("SELECT u.nombre, r.nombre FROM Usuario u JOIN u.roles r WHERE u.nombre = :nombre")
	List<Object[]> jpqlBuscarNombreYRol(@Param("nombre") String nombre);

	// Métodos @Query Nativo
	@Query(value = "SELECT * FROM usuarios u WHERE u.nombre = :nombre", nativeQuery = true)
	List<Usuario> sqlBuscarPorNombre(@Param("nombre") String nombre);

	// Métodos con proyecciones.

	List<UsuarioProyeccion> findByNombre(String nombre);

	List<NombreDTO> findByNombre(String nombre, Class<NombreDTO> projection);

	@Query(value = "SELECT nombre, email FROM usuarios WHERE nombre = :nombre", nativeQuery = true)
	List<Object[]> buscarNombreYEmail(@Param("nombre") String nombre);

}
