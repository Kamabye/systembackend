package com.herokuapp.kamabye.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.herokuapp.kamabye.models.postgresql.Paciente;

public interface IPacienteService {

	Page<Paciente> findAll(int pageNumber, int pageSize);

	Paciente buscarPorId(Integer idPaciente);

	List<Paciente> buscarPorNombre(String nombre);

	void  eliminar(Integer idPaciente);

	Long totalRegistros();

	Integer totalPaginas();

	Paciente save(Paciente paciente);

	Long countByIdPaciente(Long idPaciente);

	Paciente update(Paciente paciente);

	Paciente findByIdPaciente(Long idPaciente);

	Paciente deleteReturn(Long idUsuario);

}
