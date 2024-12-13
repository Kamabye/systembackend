package com.system.domain.repository.postgresql;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.system.domain.interfaces.DTOProyecciones.IUsuarioDTO;
import com.system.domain.models.dto.UsuarioDTO;
import com.system.domain.models.postgresql.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

	// Derived Query Methods
	// Para mejorar el rendimiento se recomiendo usar las consultas con paginación.
	
	Long countByIdUsuario(Long idUsuario);

	List<Usuario> findByNombres(String nombres);

	Page<Usuario> findByNombres(String nombres, Pageable pageable);

	Page<Usuario> findByNombresAndPrimerApellido(String nombres, String primerApellido, Pageable pageable);

	Page<Usuario> findByprimerApellidoOrSegundoApellido(String primerApellido, String segundoApellido,
			Pageable pageable);

	Page<Usuario> findByNombresLike(String partedelnombre, Pageable pageable);

	Page<Usuario> findByNombresContaining(String partedelnombre, Pageable pageable);

	Page<Usuario> findByNombresStartingWith(String iniciodelnombre, Pageable pageable);

	Page<Usuario> findByNombresEndingWith(String finaldelnombre, Pageable pageable);

	Page<Usuario> findByNombresIgnoreCase(String nombres, Pageable pageable);

	Page<Usuario> findByNombresOrderByNombresAsc(String nombres, Pageable pageable);

	List<Usuario> findByPrimerApellidoOrderByNombresAsc(String primerApellido);

	List<Usuario> findBySegundoApellidoOrderByNombresAsc(String segundoApellido);

	Optional<Usuario> findByEmail(String email);

	List<Usuario> findByNombresContainingOrPrimerApellidoContainingOrSegundoApellidoContainingOrderByNombresAsc(
			String nombres, String primerApellido, String segundoApellido);

	// Métodos @Query JPQL Java Persistence Query Language
	// Todos los métodos tienen que comenzar con jpqlFindBy

	@Query("SELECT u FROM Usuario u")
	// @QueryHints({
	// @QueryHint(name = "org.hibernate.cacheable", value = "true"),
	// @QueryHint(name = "javax.persistence.query.timeout", value = "1000")
	// })
	List<Usuario> queryObtenerUsuarios();

	@Query("SELECT u FROM Usuario u")
	Page<Usuario> jpqlObtenerUsuarios(Pageable pageable);
	
	@Query("SELECT new com.system.domain.models.dto.UsuarioDTO( u ) FROM Usuario u")
	Page<UsuarioDTO> jpqlfindAllUsuariosDTOPageable(Pageable pageable);

	@Query("SELECT new com.system.domain.models.dto.UsuarioDTO(u.id, u.nombres) FROM Usuario u")
	List<UsuarioDTO> jpqlObtenerUsuariosDTO();

	@Query("SELECT new com.system.domain.models.dto.UsuarioDTO(u.id, u.nombres) FROM Usuario u WHERE u.nombres = :nombres")
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

	/*
	 * @Query("SELECT v.producto, SUM(v.cantidad * v.precio) AS totalVentas " +
	 * "FROM Venta v " + "GROUP BY v.producto " +
	 * "HAVING SUM(v.cantidad * v.precio) > :valorMinimo") List<Object[]>
	 * findProductosConVentasMayoresA(@Param("valorMinimo") double valorMinimo);
	 * 
	 * @Query("SELECT AVG(v.precio) FROM Venta v") Double findAveragePrecio();
	 * 
	 * @Query("SELECT AVG(v.precio) FROM Venta v WHERE v.producto = :producto")
	 * Double findAveragePrecioByProducto(@Param("producto") String producto);
	 */

	// JPQL con Interfaces Proyecciones DTO

	@Query("SELECT u FROM Usuario u")
	List<IUsuarioDTO> jpqlfindAllUsuariosIDTO();

	@Query("SELECT u.id AS id, u.nombres AS nombres, u.email AS email, r.rol AS roles FROM Usuario u JOIN u.roles r")
	Page<IUsuarioDTO> jpqlfindAllUsuariosIDTOPageable(Pageable pageable);

	// JPQL con Clases Proyecciones DTO

	
	
	// Métodos @Query Nativo
	// Todos los métodos deben comenzar con sqlFindBy
	
	@Query(value = "SELECT * FROM usuarios", nativeQuery = true)
	Page<IUsuarioDTO> sqlfindAllUsuariosIDTOPageable(Pageable pageable);

	@Query(value = "SELECT * FROM usuarios WHERE nombres = ?1", nativeQuery = true)
	List<Usuario> sqlBuscarPorNombreSQL(String nombres);

	@Query(value = "SELECT AVG(precio) FROM Venta", nativeQuery = true)
	Double sqlfindAveragePrecio();

	// Métodos con proyecciones.

	@Query(value = "SELECT * FROM usuarios WHERE nombres = ?1", nativeQuery = true)
	List<IUsuarioDTO> findByNombresProyeccion(String nombres);

	@Query(value = "SELECT nombres, email FROM usuarios WHERE nombres = ?1", nativeQuery = true)
	List<Object[]> buscarNombreYEmail(String nombres);

}
