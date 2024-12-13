package com.system.domain.repository.postgresql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.domain.models.postgresql.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	Long countByIdPaciente(Long idPaciente);
	
	Page<Paciente> findByNombresContainingIgnoreCaseOrPrimerApellidoContainingIgnoreCaseOrSegundoApellidoContainingIgnoreCaseOrderByNombresAsc(
	  String nombres, String primerApellido, String segundoApellido, Pageable pageable);
	
}
