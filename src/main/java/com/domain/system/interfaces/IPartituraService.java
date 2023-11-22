package com.domain.system.interfaces;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.domain.system.models.dto.ObraDTO;
import com.domain.system.models.dto.PartituraDTO;
import com.domain.system.models.postgresql.Partitura;

public interface IPartituraService {
	
	Partitura save(Partitura partitura);
	
	Partitura guardarConMarcaDeAgua(Partitura partitura);

	List<Partitura> saveAll(List<Partitura> partituras);

	List<Partitura> findAll();

	Page<Partitura> findAllPage(Integer page, Integer size);

	List<Partitura> findByExample(Partitura partitura);

	Page<Partitura> findByExampleWithPage(Partitura partitura, Integer page, Integer size);

	void delete(Long idPartitura);

	Partitura findById(Long idPartitura);
	
	List<PartituraDTO> jpqlFindAll();
	
	//List<PartituraDTO> jpqlFindAll();
	
	Set<PartituraDTO> jpqlfindByIdObra(Long idObra);

	List<Partitura> findByInstrumento(String instrumento);

	ObraDTO jpqlfindObraByIdObra(Long idObra);

}
