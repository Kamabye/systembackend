package com.domain.system.repository.postgresql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.system.models.postgresql.Partitura;

@Repository
public interface PartituraRepository extends JpaRepository<Partitura, Long>{
	
	List<Partitura> findByInstrumentoContainingOrderByNombreAsc(String instrumento);

}
