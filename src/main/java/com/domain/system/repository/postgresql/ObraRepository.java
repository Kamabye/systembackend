package com.domain.system.repository.postgresql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.domain.system.models.dto.ObraDTO;
import com.domain.system.models.postgresql.Obra;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {

	List<Obra> findByNombreContainingOrderByNombreAsc(String nombre);
	
	List<Obra> findByCompositorContainingOrderByNombreAsc(String compositor);
	
	List<Obra> findByArreglistaContainingOrderByNombreAsc(String arreglista);
	
	List<Obra> findByLetristaContainingOrderByNombreAsc(String letrista);
	
	List<Obra> findByGeneroContainingOrderByNombreAsc(String genero);
	
	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o")
	List<ObraDTO> jpqlfindAll();
	
	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.id = :id")
	ObraDTO jpqlfindByIdObra(@Param("id") Long id);

}
