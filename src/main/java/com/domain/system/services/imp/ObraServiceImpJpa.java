package com.domain.system.services.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.system.interfaces.IObraService;
import com.domain.system.interfaces.IPartituraService;
import com.domain.system.models.dto.ObraDTO;
import com.domain.system.models.postgresql.Obra;
import com.domain.system.repository.postgresql.ObraRepository;

@Service
@Transactional
@Primary
public class ObraServiceImpJpa implements IObraService {

	@Autowired
	private IPartituraService partituraService;

	@Autowired
	private ObraRepository obraRepository;

	@Override
	public Obra save(Obra obra) {
		return obraRepository.save(obra);
	}

	@Override
	public List<Obra> saveAll(List<Obra> obras) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Obra> findAll() {
		return obraRepository.findAll();
	}

	@Override
	public Page<Obra> findAllPage(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Obra> findByExample(Obra obra) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Obra> findByExampleWithPage(Obra obra, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long idObra) {
		obraRepository.deleteById(idObra);

	}

	@Override
	public Obra findById(Long idObra) {
		Optional<Obra> obraFind = obraRepository.findById(idObra);
		if (obraFind.isPresent()) {
			return obraFind.get();
		}

		return null;
	}

	@Override
	public List<Obra> findByNombre(String nombre) {
		return obraRepository.findByNombreContainingOrderByNombreAsc(nombre);
	}

	@Override
	public List<Obra> findByCompositor(String compositor) {
		return obraRepository.findByCompositorContainingOrderByNombreAsc(compositor);
	}

	@Override
	public List<Obra> findByArreglista(String arreglista) {
		return obraRepository.findByArreglistaContainingOrderByNombreAsc(arreglista);
	}

	@Override
	public List<Obra> findByLetrista(String letrista) {
		return obraRepository.findByLetristaContainingOrderByNombreAsc(letrista);
	}

	@Override
	public List<Obra> findByGenero(String genero) {
		return obraRepository.findByGeneroContainingOrderByNombreAsc(genero);
	}

	@Override
	public List<ObraDTO> jpqlfindAll() {
		List<ObraDTO> obrasdto = obraRepository.jpqlfindAll();

		for (ObraDTO obraDTO : obrasdto) {
			obraDTO.setPartituras(partituraService.jpqlFindByObra(obraDTO.getIdObra()));
		}

		return obrasdto;
	}

	@Override
	public ObraDTO jpqlfindByIdObra(Long idObra) {
		
		ObraDTO obraDTO = obraRepository.jpqlfindByIdObra(idObra);
		
		obraDTO.setPartituras(partituraService.jpqlFindByObra(idObra));

		return obraDTO;

	}

}