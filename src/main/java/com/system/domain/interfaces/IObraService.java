package com.system.domain.interfaces;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.system.domain.models.dto.ObraDTO;
import com.system.domain.models.postgresql.Obra;

public interface IObraService {

	Obra save(Obra obra);
	
	ObraDTO saveDTO(Obra obra);

	List<Obra> saveAll(List<Obra> obras);

	List<Obra> findAll();

	Page<Obra> findAllPage(Integer page, Integer size);

	List<Obra> findByExample(Obra obra);

	Page<Obra> findByExampleWithPage(Obra obra, Integer page, Integer size);

	void delete(Long idObra);
	
	Obra deletedReturn(Long idObra);

	Obra findById(Long idObra);

	List<Obra> findByNombre(String nombre);

	List<Obra> findByCompositor(String compositor);

	List<Obra> findByArreglista(String arreglista);

	List<Obra> findByLetrista(String letrista);

	List<Obra> findByGenero(String genero);
	
	Page<ObraDTO> jpqlfindAllDTO(int pageNumber, int pageSize);
	
	ObraDTO jpqlfindByIdObra(Long idObra);
	
	Set<ObraDTO> jpqlfindByNombre(String nombre);

	Set<ObraDTO> jpqlfindByCompositor(String compositor);

	Set<ObraDTO> jpqlfindByArreglista(String arreglista);

	Set<ObraDTO> jpqlfindByLetrista(String letrista);

	Set<ObraDTO> jpqlfindByGenero(String genero);
	
	Page<ObraDTO> jpqlfindByString(int pageNumber, int pageSize, String string);
	
	Page<ObraDTO> jpqlfindByStringDTO(int pageNumber, int pageSize, String string);

}
