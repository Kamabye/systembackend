package com.herokuapp.kamabye.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.herokuapp.kamabye.models.postgresql.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
	
	
	
}
