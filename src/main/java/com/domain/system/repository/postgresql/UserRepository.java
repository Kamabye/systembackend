package com.domain.system.repository.postgresql;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.system.models.postgresql.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {

	List<Usuario> findByNombres(String nombres);

	List<Usuario> findByNombres(String nombres, Pageable pageable);

	List<Usuario> findByNombresOrderByNombresAsc(String nombres, Pageable pageable);

	List<Usuario> findByApellidoPaterno(String apellidoPaterno);

	List<Usuario> findByApellidoMaterno(String apellidoMaterno);

	Optional<Usuario> findByEmail(String email);

	List<Usuario> findByNombresContainingOrApellidoPaternoContainingOrApellidoMaternoContainingOrderByNombresAsc(
			String nombres,String apellidoPaterno, String apellidoMaterno);

}
