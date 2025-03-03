package com.system.domain.repository.postgresql;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.system.domain.model.dto.ObraDTO;
import com.system.domain.model.postgresql.Obra;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {
	
	List<Obra> findByNombreContainingOrderByNombreAsc(String nombre);
	
	List<Obra> findByCompositorContainingOrderByNombreAsc(String compositor);
	
	List<Obra> findByArreglistaContainingOrderByNombreAsc(String arreglista);
	
	List<Obra> findByLetristaContainingOrderByNombreAsc(String letrista);
	
	List<Obra> findByGeneroContainingOrderByNombreAsc(String genero);
	
	@Query("SELECT NEW com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.iva, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o ORDER BY o.nombre ASC")
	Page<ObraDTO> jpqlfindAll(Pageable pageable);
	
	@Query("SELECT NEW com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.iva, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o WHERE o.idObra = :idObra")
	Optional<ObraDTO> jpqlfindByIdObra(@Param("idObra") Long idObra);
	
	@Query("SELECT NEW com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.iva, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o WHERE o.nombre LIKE %:nombre% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByNombreContaining(@Param("nombre") String nombre);
	
	@Query("SELECT NEW com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.iva, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o WHERE o.compositor LIKE %:compositor% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByCompositorContaining(@Param("compositor") String compositor);
	
	@Query("SELECT NEW com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.iva, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o WHERE o.arreglista LIKE %:arreglista% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByArreglistaContaining(@Param("arreglista") String arreglista);
	
	@Query("SELECT NEW com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.iva, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o WHERE o.letrista LIKE %:letrista% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByLetristaContaining(@Param("letrista") String letrista);
	
	@Query("SELECT NEW com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.genero, o.precio, o.iva, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) FROM Obra o WHERE o.genero LIKE %:genero% ORDER BY o.nombre ASC")
	Set<ObraDTO> jpqlfindByGeneroContaining(@Param("genero") String genero);
	
	@Query("SELECT DISTINCT o "
	  + "FROM Obra o "
	  + "WHERE LOWER(o.nombre) LIKE LOWER(CONCAT('%', :string, '%')) "
	  + "OR LOWER(o.compositor) LIKE LOWER(CONCAT('%', :string, '%')) "
	  + "OR LOWER(o.arreglista) LIKE LOWER(CONCAT('%', :string, '%')) "
	  + "OR LOWER(o.letrista) LIKE LOWER(CONCAT('%', :string, '%')) "
	  + "OR LOWER(o.genero) LIKE LOWER(CONCAT('%', :string, '%')) ")
	Page<Obra> jpqlfindByString(@Param("string") String string, Pageable pageable);
	
	@Query("SELECT DISTINCT new com.system.domain.model.dto.ObraDTO(o.idObra, o.nombre, o.compositor, o.arreglista, o.letrista, o.precio, o.iva, o.genero, o.embedAudio, o.embedVideo, o.createdAt, o.modifiedAt) "
	  + "FROM Obra o "
	  + "WHERE LOWER(o.nombre) LIKE LOWER(CONCAT('%', :string, '%')) "
	  + "OR LOWER(o.compositor) LIKE LOWER(CONCAT('%', :string, '%')) "
	  + "OR LOWER(o.arreglista) LIKE LOWER(CONCAT('%', :string, '%')) "
	  + "OR LOWER(o.letrista) LIKE LOWER(CONCAT('%', :string, '%')) "
	  + "OR LOWER(o.genero) LIKE LOWER(CONCAT('%', :string, '%'))")
	Page<ObraDTO> jpqlfindByStringDTO(@Param("string") String string, Pageable pageable);
	
}
