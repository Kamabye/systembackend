package com.system.domain.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.domain.model.dto.ObraDTO;
import com.system.domain.model.dto.PartituraDTO;
import com.system.domain.model.postgresql.Obra;
import com.system.domain.model.postgresql.Partitura;
import com.system.domain.model.postgresql.PartituraBlob;
import com.system.domain.repository.postgresql.PartituraBlobRepository;
import com.system.domain.repository.postgresql.PartituraRepository;
import com.system.domain.service.interfaces.IPartituraService;

@Service
//@Transactional
@Primary
public class PartituraServiceImpJpa implements IPartituraService {
	
	@Autowired
	private PartituraRepository partituraRepo;
	
	@Autowired
	private PartituraBlobRepository partituraBlobRepo;
	
	@Transactional
	@Override
	public Partitura save(Partitura partitura) {
		return partituraRepo.save(partitura);
	}
	
	@Transactional
	@Override
	public PartituraDTO saveDTO(Partitura partitura) {
		
		Partitura partituraSave = partituraRepo.save(partitura);
		
		return new PartituraDTO(partituraSave.getIdPartitura(), partituraSave.getInstrumento());
	}
	
	
	//No mantiene bloqueos de escritura
	@Transactional(readOnly = true)
	@Override
	public List<Partitura> saveAll(List<Partitura> partituras) {
		return partituraRepo.saveAll(partituras);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Partitura> findAll() {
		return partituraRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Partitura> findAllPage(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Partitura> findByExample(Partitura partitura) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Partitura> findByExampleWithPage(Partitura partitura, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	@Override
	public void delete(Long idPartitura) {
		partituraRepo.deleteById(idPartitura);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Partitura findById(Long idPartitura) {
		Optional<Partitura> partitura = partituraRepo.findById(idPartitura);
		if (partitura.isPresent()) {
			return partitura.get();
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Partitura> findByInstrumento(String instrumento) {
		return partituraRepo.findByInstrumentoContainingOrderByInstrumentoAsc(instrumento);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<PartituraDTO> jpqlFindAll() {
		return partituraRepo.jpqlfindAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<PartituraDTO> jpqlfindByIdObra(Long idObra) {
		return partituraRepo.jpqlfindByIdObra(idObra);
		
	}
	
	@Transactional
	@Override
	public Partitura guardarConMarcaDeAgua(Partitura partitura) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public ObraDTO jpqlfindObraByIdObra(Long idObra) {
		Optional<ObraDTO> optional = partituraRepo.jpqlfindObraByIdObra(idObra);
		if (!optional.isEmpty()) {
			ObraDTO obraDTO = optional.get();
			obraDTO.setPartituras(partituraRepo.jpqlfindByIdObra(idObra));
			return obraDTO;
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<String> jpqlfindInstrumentos(Long idObra) {
		
		Set<String> instrumentos = partituraRepo.jpqlfindInstrumentos(idObra);
		if (!instrumentos.isEmpty()) {
			return instrumentos;
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public PartituraDTO jpqlfindById(Long idPartitura) {
		
		Optional<PartituraDTO> partitura = partituraRepo.jpqlfindById(idPartitura);
		if (!partitura.isEmpty()) {
			return partitura.get();
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<Partitura> findByObra(Obra obra) {
		return partituraRepo.findByObra(obra);
	}
	
	@Transactional
	@Override
	public PartituraBlob saveBlob(PartituraBlob partituraBlob) {
		return partituraBlobRepo.save(partituraBlob);
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public byte[] getVistaPrevia(Long idPartitura) {
		
		Optional<byte[]> optional = partituraBlobRepo.getVistaPrevia(idPartitura);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public PartituraBlob getLastPartituraBlob(Long idPartitura) {
		// TODO Auto-generated method stub
		return null;
	}
	
}