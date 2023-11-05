package com.domain.system.repository.postgresql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.system.models.postgresql.Obra;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {

	List<Obra> findByNombreContainingOrderByNombreAsc(String nombre);
	
	List<Obra> findByCompositorContainingOrderByNombreAsc(String compositor);
	
	List<Obra> findByArreglistaContainingOrderByNombreAsc(String arreglista);
	
	List<Obra> findByLetristaContainingOrderByNombreAsc(String letrista);
	
	List<Obra> findByGeneroContainingOrderByNombreAsc(String genero);

}
