package com.domain.system.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.domain.system.interfaces.IObraService;
import com.domain.system.models.postgresql.Obra;
import com.domain.system.repository.postgresql.ObraRepository;

public class ObraServiceImpJpa implements IObraService {
	
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
		// TODO Auto-generated method stub
		return null;
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
	public void delete(Integer idObra) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Obra findById(Long idObra) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Obra> findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Obra> findByCompositor(String compositor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Obra> findByArreglista(String arreglista) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Obra> findByLetrista(String letrista) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Obra> findByGenero(String genero) {
		// TODO Auto-generated method stub
		return null;
	}

}
