package com.domain.system.services.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.domain.system.interfaces.IPartituraService;
import com.domain.system.models.postgresql.Partitura;
import com.domain.system.repository.postgresql.PartituraRepository;

@Service
public class PartituraServiceImpJpa implements IPartituraService {

	@Autowired
	private PartituraRepository partituraRepo;

	@Override
	public Partitura save(Partitura partitura) {
		return partituraRepo.save(partitura);
	}

	@Override
	public List<Partitura> saveAll(List<Partitura> partituras) {
		return partituraRepo.saveAll(partituras);
	}

	@Override
	public List<Partitura> findAll() {
		return partituraRepo.findAll();
	}

	@Override
	public Page<Partitura> findAllPage(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Partitura> findByExample(Partitura partitura) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Partitura> findByExampleWithPage(Partitura partitura, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long idPartitura) {
		partituraRepo.deleteById(idPartitura);

	}

	@Override
	public Partitura findById(Long idPartitura) {
		Optional<Partitura> partitura = partituraRepo.findById(idPartitura);
		if (partitura.isPresent()) {
			return partitura.get();
		}
		return null;
	}

	@Override
	public List<Partitura> findByInstrumento(String instrumento) {
		return partituraRepo.findByInstrumentoContainingOrderByNombreAsc(instrumento);
	}

}
