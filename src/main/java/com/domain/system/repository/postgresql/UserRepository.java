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
import com.domain.system.models.dto.UsuarioDTO;
import com.domain.system.models.postgresql.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
	
	
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

	@Query("SELECT new com.domain.system.models.dto.UsuarioDTO(u.id, u.nombres) FROM Usuario u")
	List<UsuarioDTO> jpqlObtenerUsuariosDTO();

	@Query("SELECT new com.domain.system.models.dto.UsuarioDTO(u.id, u.nombres) FROM Usuario u WHERE u.nombres = :nombres")
	List<UsuarioDTO> jpqlBuscarUsuariosPorNombres(@Param("nombres") String nombres);

	@Query("SELECT u FROM Usuario u WHERE u.nombres = :nombres")
	List<Usuario> jpqlBuscarPorNombre(@Param("nombres") String nombres);

	@Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.rol = :rolNombre")
	List<Usuario> jpqlBuscarPorRol(@Param("rolNombre") String rolNombre);

	@Query("SELECT u FROM Usuario u ORDER BY u.nombres DESC")
	List<Usuario> jpqlObtenerUsuariosOrdenados();

	@Query("SELECT u FROM Usuario u WHERE (:nombres IS NULL OR u.nombres = :nombres) AND (:email IS NULL OR u.email = :email)")
	List<Usuario> jpqlBuscarUsuariosPorNombreYEmail(@Param("nombres") String nombres, @Param("email") String email);

	@Query("SELECT u.nombres, r.rol FROM Usuario u JOIN u.roles r WHERE u.nombres = :nombres")
	List<Object[]> jpqlBuscarNombreYRol(@Param("nombres") String nombres);

	// Métodos @Query Nativo
	@Query(value = "SELECT * FROM usuarios u WHERE u.nombres = :nombres", nativeQuery = true)
	List<Usuario> sqlBuscarPorNombre(@Param("nombres") String nombres);

	// Métodos con proyecciones.

	@Query(value = "SELECT * FROM usuarios u WHERE u.nombres = :nombres", nativeQuery = true)
	List<UsuarioProyeccion> findByNombresProyeccion(@Param("nombres")String nombres);
	
	@Query(value = "SELECT nombres, email FROM usuarios WHERE nombres = :nombres", nativeQuery = true)
	List<Object[]> buscarNombreYEmail(@Param("nombres") String nombres);

}
