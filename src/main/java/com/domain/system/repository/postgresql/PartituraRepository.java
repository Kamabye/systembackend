package com.domain.system.repository.postgresql;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.domain.system.models.dto.ObraDTO;
import com.domain.system.models.dto.PartituraDTO;
import com.domain.system.models.postgresql.Partitura;

@Repository
public interface PartituraRepository extends JpaRepository<Partitura, Long> {

	List<Partitura> findByInstrumentoContainingOrderByInstrumentoAsc(String instrumento);

	/**
	 * Este método hace una búsqueda por id de la Clase Obra. Primero obtiene el
	 * campo Obra y luego el id en el nombre del método
	 * 
	 * @param idObra
	 * @return
	 */
	List<Partitura> findByObraId(Long idObra);

	@Query("SELECT new com.domain.system.models.dto.PartituraDTO(p.id, p.instrumento) FROM Partitura p")
	List<PartituraDTO> jpqlfindAll();
	
	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.id = :id ORDER BY o.nombre ASC")
	Optional<ObraDTO> jpqlfindObraByIdObra(@Param("id") Long id);

	@Query("SELECT new com.domain.system.models.dto.PartituraDTO(p.id, p.instrumento) FROM Partitura p WHERE p.obra.id = :idObra ORDER BY p.instrumento ASC")
	Set<PartituraDTO> jpqlfindByIdObra(@Param("idObra") Long idObra);

}
