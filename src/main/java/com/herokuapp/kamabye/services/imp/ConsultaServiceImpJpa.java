package com.herokuapp.kamabye.services.imp;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.herokuapp.kamabye.interfaces.IConsultaService;
import com.herokuapp.kamabye.models.postgresql.Consulta;
import com.herokuapp.kamabye.models.postgresql.Paciente;
import com.herokuapp.kamabye.repository.postgresql.ConsultaRepository;

@Service
@Primary
public class ConsultaServiceImpJpa implements IConsultaService {
	
	@Autowired
	private ConsultaRepository consultaRepo;
	
	@Override
	public Consulta guardar(Consulta consulta) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void eliminar(Integer idConsulta) {
		// TODO Auto-generated method stub
		
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
	
}
