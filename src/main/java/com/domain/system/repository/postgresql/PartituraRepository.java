package com.domain.system.repository.postgresql;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.domain.system.models.dto.PartituraDTO;
import com.domain.system.models.postgresql.Partitura;

@Repository
public interface PartituraRepository extends JpaRepository<Partitura, Long>{
	
	List<Partitura> findByInstrumentoContainingOrderByInstrumentoAsc(String instrumento);
	
	List<Partitura> findByObraId(Long idObra);
	
	@Query("SELECT new com.domain.system.models.dto.PartituraDTO(u.id, u.instrumento, u.obra.id) FROM Partitura u")
	List<PartituraDTO> jpqlfindAll();
	
	@Query("SELECT new com.domain.system.models.dto.PartituraDTO(p.id, p.instrumento) FROM Partitura p WHERE p.obra.id = :idObra")
	Set<PartituraDTO> jpqlfindByObraId(@Param("idObra") Long idObra);

}
