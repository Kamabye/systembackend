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
	public Consulta patchUpdate(Consulta consultaPatch) {
		Consulta saveConsulta = this.findByIdConsulta(consultaPatch.getIdConsulta());
		
		if (saveConsulta != null) {
			
			// if (consultaPatch.getPaciente() != null) {
			// saveConsulta.setPaciente(consultaPatch.getPaciente());
			// }
			
			mergePatchFields(saveConsulta, consultaPatch);
			
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
	
	private void mergePatchFields(Consulta existingConsulta, Consulta consultaPatch) {
		// TODO Auto-generated method stub
		
		if (consultaPatch.getTotalDer() != null && !consultaPatch.getTotalDer().equals(existingConsulta.getTotalDer())) {
			existingConsulta.setTotalDer(consultaPatch.getTotalDer());
		}
		if (consultaPatch.getTotalIzq() != null && !consultaPatch.getTotalIzq().equals(existingConsulta.getTotalIzq())) {
			existingConsulta.setTotalIzq(consultaPatch.getTotalIzq());
		}
		
		if (consultaPatch.getTotalAstigDer() != null && !consultaPatch.getTotalAstigDer().equals(existingConsulta.getTotalAstigDer())) {
			existingConsulta.setTotalAstigDer(consultaPatch.getTotalAstigDer());
		}
		if (consultaPatch.getTotalAstigIzq() != null && !consultaPatch.getTotalAstigIzq().equals(existingConsulta.getTotalAstigIzq())) {
			existingConsulta.setTotalAstigIzq(consultaPatch.getTotalAstigIzq());
		}
		
		if (consultaPatch.getTotalAngDer() != null && !consultaPatch.getTotalAngDer().equals(existingConsulta.getTotalAngDer())) {
			existingConsulta.setTotalAngDer(consultaPatch.getTotalAngDer());
		}
		if (consultaPatch.getTotalAngIzq() != null && !consultaPatch.getTotalAngIzq().equals(existingConsulta.getTotalAngIzq())) {
			existingConsulta.setTotalAngIzq(consultaPatch.getTotalAngIzq());
		}
		
		if (consultaPatch.getSubLejDer() != null && !consultaPatch.getSubLejDer().equals(existingConsulta.getSubLejDer())) {
			existingConsulta.setSubLejDer(consultaPatch.getSubLejDer());
		}
		if (consultaPatch.getSubLejIzq() != null && !consultaPatch.getSubLejIzq().equals(existingConsulta.getSubLejIzq())) {
			existingConsulta.setSubLejIzq(consultaPatch.getSubLejIzq());
		}
		
		if (consultaPatch.getSubLejAstigDer() != null && !consultaPatch.getSubLejAstigDer().equals(existingConsulta.getSubLejAstigDer())) {
			existingConsulta.setSubLejAstigDer(consultaPatch.getSubLejAstigDer());
		}
		if (consultaPatch.getSubLejAstigIzq() != null && !consultaPatch.getSubLejAstigIzq().equals(existingConsulta.getSubLejAstigIzq())) {
			existingConsulta.setSubLejAstigIzq(consultaPatch.getSubLejAstigIzq());
		}
		
		if (consultaPatch.getSubLejAngDer() != null && !consultaPatch.getSubLejAngDer().equals(existingConsulta.getSubLejAngDer())) {
			existingConsulta.setSubLejAngDer(consultaPatch.getSubLejAngDer());
		}
		if (consultaPatch.getSubLejAngIzq() != null && !consultaPatch.getSubLejAngIzq().equals(existingConsulta.getSubLejAngIzq())) {
			existingConsulta.setSubLejAngIzq(consultaPatch.getSubLejAngIzq());
		}
		
		if (consultaPatch.getAddDer() != null && !consultaPatch.getAddDer().equals(existingConsulta.getAddDer())) {
			existingConsulta.setAddDer(consultaPatch.getAddDer());
		}
		if (consultaPatch.getAddIzq() != null && !consultaPatch.getAddIzq().equals(existingConsulta.getAddIzq())) {
			existingConsulta.setAddIzq(consultaPatch.getAddIzq());
		}
		
		if (consultaPatch.getAVDer() != null && !consultaPatch.getAVDer().equals(existingConsulta.getAVDer())) {
			existingConsulta.setAVDer(consultaPatch.getAVDer());
		}
		if (consultaPatch.getAVIzq() != null && !consultaPatch.getAVIzq().equals(existingConsulta.getAVIzq())) {
			existingConsulta.setAVIzq(consultaPatch.getAVIzq());
		}
		
		if (consultaPatch.getDip() != null && !consultaPatch.getDip().equals(existingConsulta.getDip())) {
			existingConsulta.setDip(consultaPatch.getDip());
		}
		
		if (consultaPatch.getRx() != null && !consultaPatch.getRx().isBlank() && !consultaPatch.getRx().isEmpty() && !consultaPatch.getRx().equals(existingConsulta.getRx())) {
			existingConsulta.setRx(consultaPatch.getRx());
		}
	}
	
}
