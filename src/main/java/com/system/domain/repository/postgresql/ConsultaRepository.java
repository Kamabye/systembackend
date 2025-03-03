package com.system.domain.repository.postgresql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.domain.model.postgresql.Consulta;


@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
	
	Long countByIdConsulta(Long idConsulta);
	
	Page<Consulta> findByPacienteIdPaciente(Long idPaciente, Pageable pageable);
	
}
