package com.system.domain.repository.postgresql;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.domain.models.postgresql.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
	
	Optional<Rol> findByRol(String rol);

}
