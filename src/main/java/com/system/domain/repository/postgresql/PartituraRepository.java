package com.system.domain.repository.postgresql;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.system.domain.model.dto.ObraDTO;
import com.system.domain.model.dto.PartituraDTO;
import com.system.domain.model.postgresql.Obra;
import com.system.domain.model.postgresql.Partitura;

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
	Set<Partitura> findByObra(Obra obra);

	@Query("SELECT new com.system.domain.model.dto.PartituraDTO(p.idPartitura, p.instrumento) FROM Partitura p")
	List<PartituraDTO> jpqlfindAll();

	@Query("SELECT NEW com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.idObra = :idObra ORDER BY o.nombre ASC")
	Optional<ObraDTO> jpqlfindObraByIdObra(@Param("idObra") Long idObra);

	@Query("SELECT new com.system.domain.model.dto.PartituraDTO(p.idPartitura, p.instrumento) FROM Partitura p WHERE p.obra.idObra = :idObra ORDER BY p.instrumento ASC")
	Set<PartituraDTO> jpqlfindByIdObra(@Param("idObra") Long idObra);

	@Query("SELECT DISTINCT p.instrumento FROM Partitura p WHERE p.obra.idObra = :idObra ORDER BY p.instrumento ASC")
	Set<String> jpqlfindInstrumentos(@Param("idObra") Long idObra);

	@Query("SELECT new com.system.domain.model.dto.PartituraDTO(p.idPartitura, p.instrumento) FROM Partitura p WHERE p.idPartitura = :idPartitura")
	Optional<PartituraDTO> jpqlfindById(@Param("idPartitura") Long idPartitura);

}
