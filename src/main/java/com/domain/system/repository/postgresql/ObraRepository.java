package com.domain.system.repository.postgresql;

import java.util.List;
import java.util.Optional;
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

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindAll();

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o WHERE o.id = :id")
	Optional<ObraDTO> jpqlfindByIdObra(@Param("id") Long id);

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.nombre LIKE %:nombre% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByNombreContaining(@Param("nombre") String nombre);

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.compositor LIKE %:compositor% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByCompositorContaining(@Param("compositor") String compositor);

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.arreglista LIKE %:arreglista% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByArreglistaContaining(@Param("arreglista") String arreglista);

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.letrista LIKE %:letrista% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByLetristaContaining(@Param("letrista") String letrista);

	@Query("SELECT NEW com.domain.system.models.dto.ObraDTO(o.id, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio) FROM Obra o WHERE o.genero LIKE %:genero% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByGeneroContaining(@Param("genero") String genero);

}
