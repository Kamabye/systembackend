package com.herokuapp.kamabye.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.herokuapp.kamabye.models.postgresql.Consulta;
import com.herokuapp.kamabye.models.postgresql.Paciente;

public interface IConsultaService {
	
	Consulta guardar(Consulta consulta);

	void eliminar(Integer idConsulta);

	Long totalRegistros();

	Integer totalPaginas();

	List<Consulta> buscarPorPaciente(Paciente paciente);

	List<Consulta> buscarPorIDPaciente(Integer idPaciente);

	Consulta ultimaConsulta(Paciente paciente);

	Page<Consulta> findAll(int pageNumber, int pageSize);

	Consulta findByIdConsulta(Long idConsulta);
	
}
