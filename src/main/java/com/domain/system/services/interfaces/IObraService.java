package com.domain.system.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.domain.system.models.postgresql.Obra;

public interface IObraService {

	Obra save(Obra obra);

	List<Obra> saveAll(List<Obra> obras);

	List<Obra> findAll();

	Page<Obra> findAllPage(Integer page, Integer size);

	List<Obra> findByExample(Obra obra);

	Page<Obra> findByExampleWithPage(Obra obra, Integer page, Integer size);

	void delete(Integer idObra);

	Obra findById(Long idObra);

	List<Obra> findByNombre(String nombre);

	List<Obra> findByCompositor(String compositor);

	List<Obra> findByArreglista(String arreglista);

	List<Obra> findByLetrista(String letrista);

	List<Obra> findByGenero(String genero);

}
