package com.system.domain.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.system.domain.model.postgresql.Consulta;
import com.system.domain.model.postgresql.Paciente;

public interface IPacienteService {
	
	Long countById(Long idPaciente);
	
	Page<Paciente> findAll(int pageNumber, int pageSize);
	
	Paciente findById(Long idPaciente);
	
	Page<Paciente> findByString(int pageNumber, int pageSize, String string);
	
	Paciente save(Paciente paciente);
	
	List<Paciente> saveList(List<Paciente> pacientes);
	
	List<Paciente> saveListAndFlush(List<Paciente> pacientes);
	
	Paciente putUpdate(Paciente paciente);
	
	List<Paciente> putUpdateList(List<Paciente> pacientes);
	
	Paciente patchUpdate(Paciente paciente);
	
	List<Paciente> patchUpdateList(List<Paciente> pacientes);
	
	void delete(Long idPaciente);
	
	Paciente deleteAndReturn(Long idUsuario);
	
	Page<Consulta> getConsultas(int pageNumber, int pageSize);
	
}
