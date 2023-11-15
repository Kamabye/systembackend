package com.domain.system.repository.postgresql;

import java.util.List;
import java.util.Set;

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
	
	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.nombre LIKE %:nombre%")
	Set<ObraDTO> jpqlfindByNombreContaining(@Param("nombre") String nombre);

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.compositor LIKE %:compositor%")
	Set<ObraDTO> jpqlfindByCompositorContaining(@Param("compositor") String compositor);

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.arreglista LIKE %:arreglista%")
	Set<ObraDTO> jpqlfindByArreglistaContaining(@Param("arreglista") String arreglista);

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.letrista LIKE %:letrista%")
	Set<ObraDTO> jpqlfindByLetristaContaining(String letrista);
	
	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.genero LIKE %:genero%")
	Set<ObraDTO> jpqlfindByGeneroContaining(String genero);

}
