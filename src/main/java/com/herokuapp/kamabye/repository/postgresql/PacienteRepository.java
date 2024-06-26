package com.herokuapp.kamabye.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.herokuapp.kamabye.models.postgresql.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	Long countByIdPaciente(Long idPaciente);
	
}
