package com.system.domain.service.interfaces;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.system.domain.model.dto.ObraDTO;
import com.system.domain.model.dto.PartituraDTO;
import com.system.domain.model.postgresql.Obra;
import com.system.domain.model.postgresql.Partitura;
import com.system.domain.model.postgresql.PartituraBlob;

public interface IPartituraService {
	
	Partitura save(Partitura partitura);
	
	PartituraDTO saveDTO(Partitura partitura);
	
	Partitura guardarConMarcaDeAgua(Partitura partitura);
	
	List<Partitura> saveAll(List<Partitura> partituras);
	
	List<Partitura> findAll();
	
	Page<Partitura> findAllPage(Integer page, Integer size);
	
	List<Partitura> findByExample(Partitura partitura);
	
	Page<Partitura> findByExampleWithPage(Partitura partitura, Integer page, Integer size);
	
	void delete(Long idPartitura);
	
	Partitura findById(Long idPartitura);
	
	List<PartituraDTO> jpqlFindAll();
	
	// List<PartituraDTO> jpqlFindAll();
	
	Set<PartituraDTO> jpqlfindByIdObra(Long idObra);
	
	Set<Partitura> findByObra(Obra obra);
	
	List<Partitura> findByInstrumento(String instrumento);
	
	ObraDTO jpqlfindObraByIdObra(Long idObra);
	
	Set<String> jpqlfindInstrumentos(Long idObra);
	
	PartituraDTO jpqlfindById(Long idPartitura);
	
	PartituraBlob saveBlob(PartituraBlob partituraBlob);
	
	byte[] getVistaPrevia(Long idPartitura);
	
	PartituraBlob getLastPartituraBlob(Long idPartitura);
	
}