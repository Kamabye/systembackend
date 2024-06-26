package com.herokuapp.kamabye.services.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herokuapp.kamabye.interfaces.IPacienteService;
import com.herokuapp.kamabye.models.postgresql.Consulta;
import com.herokuapp.kamabye.models.postgresql.Paciente;
import com.herokuapp.kamabye.repository.postgresql.PacienteRepository;

@Service
@Primary
public class PacienteServiceImpJpa implements IPacienteService {
	
	@Autowired
	private PacienteRepository pacienteRepo;
	
	@Override
	public Page<Paciente> findAll(int pageNumber, int pageSize) {
		
		Sort.Order order3 = new Sort.Order(Sort.Direction.ASC, "nombres");
		
		Page<Paciente> pagePacientes = pacienteRepo.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(order3)));
		
		return pagePacientes;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Paciente findByIdPaciente(Long idPaciente) {
		
		/*
		 * Optional<Usuario> optional = userRepo.findById(idUsuario); if
		 * (!optional.isEmpty()) { return optional.get(); } return null;
		 */
		
		return pacienteRepo.findById(idPaciente).orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con ID: " + idPaciente));
	}
	
	@Override
	public Paciente buscarPorId(Integer idPaciente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Paciente> buscarPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void eliminar(Integer idPaciente) {
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
	public Paciente save(Paciente paciente) {
		
		return pacienteRepo.save(paciente);
	}
	
	@Override
	public Long countByIdPaciente(Long idPaciente) {
		return pacienteRepo.countByIdPaciente(idPaciente);
	}
	
	@Override
	public Paciente update(Paciente paciente) {
		
		Paciente pacientePrevio = this.findByIdPaciente(paciente.getIdPaciente());
		
		return pacienteRepo.save(pacientePrevio);
	}
	
	@Transactional
	@Override
	public Paciente deleteReturn(Long idPaciente) {
		Optional<Paciente> optional = pacienteRepo.findById(idPaciente);
		if (!optional.isEmpty()) {
			pacienteRepo.deleteById(idPaciente);
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Consulta> getConsultas(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
