package com.system.domain.services.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.system.domain.interfaces.IConsultaService;
import com.system.domain.models.postgresql.Consulta;
import com.system.domain.models.postgresql.Paciente;
import com.system.domain.repository.postgresql.ConsultaRepository;

@Service
@Primary
public class ConsultaServiceImpJpa implements IConsultaService {
	
	@Autowired
	private ConsultaRepository consultaRepo;
	
	@Override
	public Consulta save(Consulta consulta) {
		return consultaRepo.save(consulta);
	}
	
	@Override
	public void eliminar(Integer idConsulta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Consulta deleteReturn(Long idConsulta) {
		
		Optional<Consulta> optional = consultaRepo.findById(idConsulta);
		if (!optional.isEmpty()) {
			consultaRepo.deleteById(idConsulta);
			return optional.get();
		}
		return null;
	}
	
	@Override
	public Long totalRegistros() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer totalPaginas() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Consulta> buscarPorPaciente(Paciente paciente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Consulta> buscarPorIDPaciente(Integer idPaciente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Consulta ultimaConsulta(Paciente paciente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Page<Consulta> findAll(int pageNumber, int pageSize) {
		Sort.Order order3 = new Sort.Order(Sort.Direction.DESC, "idConsulta");
		
		Page<Consulta> pageConsultas = consultaRepo.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(order3)));
		
		return pageConsultas;
	}
	
	@Override
	public Consulta findByIdConsulta(Long idConsulta) {
		
		return consultaRepo.findById(idConsulta)
		  .orElseThrow(() -> new NoSuchElementException("Consulta no encontrado con ID: " + idConsulta));
	}
	
	@Override
	public Long countByIdConsulta(Long idConsulta) {
		return consultaRepo.countByIdConsulta(idConsulta);
	}
	
	@Override
	public Consulta putUpdate(Consulta consulta) {
		return consultaRepo.save(consulta);
	}
	
	@Override
	public Page<Consulta> findByIdPaciente(Long idPaciente, int pageNumber, int pageSize) {
		Sort.Order order3 = new Sort.Order(Sort.Direction.DESC, "idConsulta");
		
		Page<Consulta> pageConsultas = consultaRepo.findByPacienteIdPaciente(idPaciente, PageRequest.of(pageNumber, pageSize, Sort.by(order3)));
		
		return pageConsultas;
	}
	
	@Override
	public Consulta patchUpdate(Consulta consulta) {
		Consulta saveConsulta = this.findByIdConsulta(consulta.getIdConsulta());
		
		if (saveConsulta != null) {
			
			if (consulta.getPaciente() != null) {
				saveConsulta.setPaciente(consulta.getPaciente());
			}
			
			return consultaRepo.save(saveConsulta);
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Paciente findPacienteByIdConsulta(Long idConsulta) {
		Consulta consulta = consultaRepo.findById(idConsulta).orElseThrow(() -> new NoSuchElementException("Consulta no encontrado con ID: " + idConsulta));
		return consulta.getPaciente();
	}
	
}
