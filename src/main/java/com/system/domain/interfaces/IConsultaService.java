package com.system.domain.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.system.domain.models.postgresql.Consulta;
import com.system.domain.models.postgresql.Paciente;

public interface IConsultaService {
	
	Consulta save(Consulta consulta);

	void eliminar(Integer idConsulta);

	Long totalRegistros();

	Integer totalPaginas();

	List<Consulta> buscarPorPaciente(Paciente paciente);

	List<Consulta> buscarPorIDPaciente(Integer idPaciente);

	Consulta ultimaConsulta(Paciente paciente);

	Page<Consulta> findAll(int pageNumber, int pageSize);

	Consulta findByIdConsulta(Long idConsulta);

	Long countByIdConsulta(Long idConsulta);

	Consulta putUpdate(Consulta consulta);

	Consulta deleteReturn(Long idConsulta);

	Page<Consulta> findByIdPaciente(Long idPaciente, int pageNumber, int pageSize);

	Consulta patchUpdate(Consulta consulta);
	
}
