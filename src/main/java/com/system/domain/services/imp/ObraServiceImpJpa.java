package com.system.domain.services.imp;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	private ObraRepository obraRepo;
	
	@Transactional
	@Override
	public Obra save(Obra obra) {
		return obraRepo.save(obra);
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
		return obraRepo.findAll();
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
		obraRepo.deleteById(idObra);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public Obra findById(Long idObra) {
		Optional<Obra> obraFind = obraRepo.findById(idObra);
		if (!obraFind.isEmpty()) {
			return obraFind.get();
		}
		
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByNombre(String nombre) {
		return obraRepo.findByNombreContainingOrderByNombreAsc(nombre);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByCompositor(String compositor) {
		return obraRepo.findByCompositorContainingOrderByNombreAsc(compositor);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByArreglista(String arreglista) {
		return obraRepo.findByArreglistaContainingOrderByNombreAsc(arreglista);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByLetrista(String letrista) {
		return obraRepo.findByLetristaContainingOrderByNombreAsc(letrista);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Obra> findByGenero(String genero) {
		return obraRepo.findByGeneroContainingOrderByNombreAsc(genero);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<ObraDTO> jpqlfindAllDTO(int pageNumber, int pageSize) {
		
		Sort sort = Sort.by(
		  Sort.Order.asc("nombre"),
		  Sort.Order.asc("compositor"),
		  Sort.Order.asc("arreglista"),
		  Sort.Order.asc("letrista"),
		  Sort.Order.asc("genero"));
		
		Page<ObraDTO> obrasdto = obraRepo.jpqlfindAll(PageRequest.of(pageNumber, pageSize, sort));
		
		for (ObraDTO obraDTO : obrasdto) {
			obraDTO.setPartituras(partituraService.jpqlfindByIdObra(obraDTO.getIdObra()));
		}
		
		return obrasdto;
	}
	
	@Transactional(readOnly = true)
	@Override
	public ObraDTO jpqlfindByIdObra(Long idObra) {
		
		Optional<ObraDTO> optional = obraRepo.jpqlfindByIdObra(idObra);
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
		
		return obraRepo.jpqlfindByNombreContaining(nombre);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByCompositor(String compositor) {
		
		return obraRepo.jpqlfindByCompositorContaining(compositor);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByArreglista(String arreglista) {
		return obraRepo.jpqlfindByArreglistaContaining(arreglista);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByLetrista(String letrista) {
		return obraRepo.jpqlfindByLetristaContaining(letrista);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<ObraDTO> jpqlfindByGenero(String genero) {
		// TODO Auto-generated method stub
		return obraRepo.jpqlfindByGeneroContaining(genero);
	}
	
	@Transactional
	@Override
	public Obra deletedReturn(Long idObra) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<ObraDTO> jpqlfindByString(int pageNumber, int pageSize, String string) {
		
		Sort sort = Sort.by(
		  Sort.Order.asc("nombre"),
		  Sort.Order.asc("compositor"),
		  Sort.Order.asc("arreglista"),
		  Sort.Order.asc("letrista"),
		  Sort.Order.asc("genero"));
		Page<Obra> obras = obraRepo.jpqlfindByString(string, PageRequest.of(pageNumber, pageSize, sort));
		return obras
		  .map(obra -> new ObraDTO(
		    obra.getIdObra(),
		    obra.getNombre(),
		    obra.getCompositor(),
		    obra.getArreglista(),
		    obra.getLetrista(),
		    obra.getGenero(),
		    obra.getPrecio(),
		    obra.getEmbedAudio(),
		    obra.getEmbedVideo(),
		    obra.getCreatedAt(),
		    obra.getModifiedAt()
		  		));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<ObraDTO> jpqlfindByStringDTO(int pageNumber, int pageSize, String string) {
		
		Sort sort = Sort.by(
		  Sort.Order.asc("nombre"),
		  Sort.Order.asc("compositor"),
		  Sort.Order.asc("arreglista"),
		  Sort.Order.asc("letrista"),
		  Sort.Order.asc("genero"));
		
		Page<ObraDTO> obrasDTO = obraRepo.jpqlfindByStringDTO(string, PageRequest.of(pageNumber, pageSize, sort));
		
		for (ObraDTO obraDTO : obrasDTO) {
			obraDTO.setPartituras(partituraService.jpqlfindByIdObra(obraDTO.getIdObra()));
		}
		
		return obrasDTO;
		
		
	}
	
}