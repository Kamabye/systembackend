package com.system.domain.services.imp;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.domain.interfaces.IObraService;
import com.system.domain.interfaces.IPartituraService;
import com.system.domain.models.dto.ObraDTO;
import com.system.domain.models.postgresql.Obra;
import com.system.domain.repository.postgresql.ObraRepository;

@Service
//@Transactional
@Primary
public class ObraServiceImpJpa implements IObraService {

	@Autowired
	private IPartituraService partituraService;

	@Autowired
	private ObraRepository obraRepository;

	@Transactional
	@Override
	public Obra save(Obra obra) {
		return obraRepository.save(obra);
	}

	@Transactional
	@Override
	public List<Obra> saveAll(List<Obra> obras) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Obra> findAll() {
		return obraRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Obra> findAllPage(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByExample(Obra obra) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Obra> findByExampleWithPage(Obra obra, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void delete(Long idObra) {
		obraRepository.deleteById(idObra);

	}

	@Transactional(readOnly = true)
	@Override
	public Obra findById(Long idObra) {
		Optional<Obra> obraFind = obraRepository.findById(idObra);
		if (!obraFind.isEmpty()) {
			return obraFind.get();
		}

		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByNombre(String nombre) {
		return obraRepository.findByNombreContainingOrderByNombreAsc(nombre);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByCompositor(String compositor) {
		return obraRepository.findByCompositorContainingOrderByNombreAsc(compositor);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByArreglista(String arreglista) {
		return obraRepository.findByArreglistaContainingOrderByNombreAsc(arreglista);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByLetrista(String letrista) {
		return obraRepository.findByLetristaContainingOrderByNombreAsc(letrista);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByGenero(String genero) {
		return obraRepository.findByGeneroContainingOrderByNombreAsc(genero);
	}

	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindAll() {
		Set<ObraDTO> obrasdto = obraRepository.jpqlfindAll();

		for (ObraDTO obraDTO : obrasdto) {
			obraDTO.setPartituras(partituraService.jpqlfindByIdObra(obraDTO.getIdObra()));
		}

		return obrasdto;
	}

	@Transactional(readOnly = true)
	@Override
	public ObraDTO jpqlfindByIdObra(Long idObra) {

		Optional<ObraDTO> optional = obraRepository.jpqlfindByIdObra(idObra);
		if (!optional.isEmpty()) {
			ObraDTO obraDTO = optional.get();
			obraDTO.setPartituras(partituraService.jpqlfindByIdObra(idObra));
			return obraDTO;
		}
		return null;

	}

	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByNombre(String nombre) {

		return obraRepository.jpqlfindByNombreContaining(nombre);
	}

	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByCompositor(String compositor) {

		return obraRepository.jpqlfindByCompositorContaining(compositor);
	}

	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByArreglista(String arreglista) {
		return obraRepository.jpqlfindByArreglistaContaining(arreglista);
	}

	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByLetrista(String letrista) {
		return obraRepository.jpqlfindByLetristaContaining(letrista);
	}

	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByGenero(String genero) {
		// TODO Auto-generated method stub
		return obraRepository.jpqlfindByGeneroContaining(genero);
	}

	@Transactional
	@Override
	public Obra deletedReturn(Long idObra) {
		// TODO Auto-generated method stub
		return null;
	}

}