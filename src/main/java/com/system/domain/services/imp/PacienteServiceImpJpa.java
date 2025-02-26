package com.system.domain.services.imp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
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

import com.system.domain.interfaces.IPacienteService;
import com.system.domain.models.postgresql.Consulta;
import com.system.domain.models.postgresql.Paciente;
import com.system.domain.repository.postgresql.PacienteRepository;

@Service
@Primary
public class PacienteServiceImpJpa implements IPacienteService {
	
	@Autowired
	private PacienteRepository pacienteRepo;
	
	@Transactional(readOnly = true)
	@Override
	public Long countById(Long idPaciente) {
		return pacienteRepo.countByIdPaciente(idPaciente);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Paciente> findAll(int pageNumber, int pageSize) {
		
		// Sort.Order order3 = new Sort.Order(Sort.Direction.ASC, "nombres");
		
		// Page<Paciente> pagePacientes =
		// pacienteRepo.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(order3)));
		
		Sort sort = Sort.by(
		  Sort.Order.asc("nombres"),
		  Sort.Order.asc("segundoApellido"),
		  Sort.Order.asc("primerApellido"));
		
		Page<Paciente> pagePacientes = pacienteRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));
		
		return pagePacientes;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Paciente findById(Long idPaciente) {
		
		/*
		 * Optional<Usuario> optional = userRepo.findById(idUsuario); if
		 * (!optional.isEmpty()) { return optional.get(); } return null;
		 */
		
		return pacienteRepo.findById(idPaciente).orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con ID: " + idPaciente));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Paciente> findByString(int pageNumber, int pageSize, String string) {
		
		Sort sort = Sort.by(
		  Sort.Order.asc("nombres"),
		  Sort.Order.asc("primerApellido"),
		  Sort.Order.asc("segundoApellido"));
		
		return pacienteRepo.findByNombresContainingIgnoreCaseOrPrimerApellidoContainingIgnoreCaseOrSegundoApellidoContainingIgnoreCaseOrderByNombresAsc(string, string, string, PageRequest.of(pageNumber, pageSize, sort));
	}
	
	@Override
	public Paciente save(Paciente paciente) {
		
		return pacienteRepo.save(paciente);
	}
	
	@Override
	public List<Paciente> saveList(List<Paciente> pacientes) {
		return pacienteRepo.saveAll(pacientes);
	}
	
	@Override
	public List<Paciente> saveListAndFlush(List<Paciente> pacientes) {
		
		System.out.println("saveListAndFlush");
		return pacienteRepo.saveAllAndFlush(pacientes);
	}
	
	@Override
	public Paciente putUpdate(Paciente paciente) {
		return pacienteRepo.save(paciente);
	}
	
	@Override
	public List<Paciente> putUpdateList(List<Paciente> pacientes) {
		return pacienteRepo.saveAll(pacientes);
	}
	
	@Override
	public Paciente patchUpdate(Paciente pacientePatch) {
		
		Paciente existingPaciente = this.findById(pacientePatch.getIdPaciente());
		
		if (existingPaciente != null) {
			
			mergePatchFields(existingPaciente, pacientePatch);
			
			return pacienteRepo.save(existingPaciente);
		}
		
		return null;
	}
	
	@Override
	public List<Paciente> patchUpdateList(List<Paciente> pacientes) {
		System.out.println("PacienteServiceImpJpa patchUpdateList");
		
		List<Paciente> listExistingPacientes = new ArrayList<>();
		
		Paciente existingPaciente = null;
		
		for (Paciente paciente : pacientes) {
			existingPaciente = this.findById(paciente.getIdPaciente());
			
			if (existingPaciente != null) {
				this.mergePatchFields(existingPaciente, paciente);
				listExistingPacientes.add(existingPaciente);
			} else {
				listExistingPacientes.add(paciente);
			}
		}
		
		return this.saveListAndFlush(listExistingPacientes);
	}
	
	@Override
	public void delete(Long idPaciente) {
		pacienteRepo.deleteById(idPaciente);
		
	}
	
	@Transactional
	@Override
	public Paciente deleteAndReturn(Long idPaciente) {
		Optional<Paciente> optional = pacienteRepo.findById(idPaciente);
		if (!optional.isEmpty()) {
			// pacienteRepo.deleteById(idPaciente);
			this.delete(idPaciente);
			return optional.get();
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Consulta> getConsultas(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void mergePatchFields(Paciente existingPaciente, Paciente pacientePatch) {
		
		if (pacientePatch.getNombres() != null && !pacientePatch.getNombres().isBlank() && !pacientePatch.getNombres().isEmpty() && !pacientePatch.getNombres().equals(existingPaciente.getNombres())) {
			existingPaciente.setNombres(pacientePatch.getNombres());
		}
		
		if (pacientePatch.getPrimerApellido() != null && !pacientePatch.getPrimerApellido().isBlank() && !pacientePatch.getPrimerApellido().isEmpty() && !pacientePatch.getPrimerApellido().equals(existingPaciente.getPrimerApellido())) {
			existingPaciente.setPrimerApellido(pacientePatch.getPrimerApellido());
		}
		
		if (pacientePatch.getSegundoApellido() != null && !pacientePatch.getSegundoApellido().isBlank() && !pacientePatch.getSegundoApellido().isEmpty() && !pacientePatch.getSegundoApellido().equals(existingPaciente.getSegundoApellido())) {
			existingPaciente.setSegundoApellido(pacientePatch.getSegundoApellido());
		}
		
		if (pacientePatch.getEdad() != null && pacientePatch.getEdad() > 0 && !pacientePatch.getEdad().equals(existingPaciente.getEdad())) {
			existingPaciente.setEdad(pacientePatch.getEdad());
		}
		
		if (pacientePatch.getFechaNacimiento() != null) {
			
			if (pacientePatch.getFechaNacimiento().isBefore(LocalDate.now()) && !pacientePatch.getFechaNacimiento().equals(existingPaciente.getFechaNacimiento())) {
				existingPaciente.setFechaNacimiento(pacientePatch.getFechaNacimiento());
			}
		}
		
		if (pacientePatch.getFechaCreacion() != null) {
			
			if (pacientePatch.getFechaCreacion().isBefore(OffsetDateTime.now()) && !pacientePatch.getFechaCreacion().equals(existingPaciente.getFechaCreacion())) {
				existingPaciente.setFechaCreacion(pacientePatch.getFechaCreacion());
			}
		}
		
		if (pacientePatch.getFechaModificacion() != null) {
			
			if (pacientePatch.getFechaModificacion().isBefore(OffsetDateTime.now()) && !pacientePatch.getFechaModificacion().equals(existingPaciente.getFechaModificacion())) {
				existingPaciente.setFechaModificacion(pacientePatch.getFechaModificacion());
			}
		}
		
		if (pacientePatch.getSexo() != null && !pacientePatch.getSexo().isBlank() && !pacientePatch.getSexo().isEmpty() && !pacientePatch.getSexo().equals(existingPaciente.getSexo())) {
			existingPaciente.setSexo(pacientePatch.getSexo());
		}
		
		if (pacientePatch.getCelular() != null && !pacientePatch.getCelular().isBlank() && !pacientePatch.getCelular().isEmpty() && !pacientePatch.getCelular().equals(existingPaciente.getCelular())) {
			existingPaciente.setCelular(pacientePatch.getCelular());
		}
		
		if (pacientePatch.getTelefono() != null && !pacientePatch.getTelefono().isBlank() && !pacientePatch.getTelefono().isEmpty() && !pacientePatch.getTelefono().equals(existingPaciente.getTelefono())) {
			existingPaciente.setTelefono(pacientePatch.getTelefono());
		}
		
		if (pacientePatch.getEmail() != null && !pacientePatch.getEmail().isBlank() && !pacientePatch.getEmail().isEmpty() && !pacientePatch.getEmail().equals(existingPaciente.getEmail())) {
			existingPaciente.setEmail(pacientePatch.getEmail());
		}
		
		if (pacientePatch.getDireccion() != null && !pacientePatch.getDireccion().isBlank() && !pacientePatch.getDireccion().isEmpty() && !pacientePatch.getDireccion().equals(existingPaciente.getDireccion())) {
			existingPaciente.setDireccion(pacientePatch.getDireccion());
		}
		
		if (pacientePatch.getConsultas() != null && !pacientePatch.getConsultas().isEmpty()) {
			for (Consulta consulta : pacientePatch.getConsultas()) {
				System.out.println("idConsulta = " + consulta.getIdConsulta());
				if (existingPaciente.hasConsulta(consulta.getIdConsulta())) {
					
					// Aqui se deberían actualizar todos los atributos presentes de la consulta,
					// pero por el momento lo agregamos sin importar esto
					// No es necesario agregar a las aconsultas porque ya tiene el IDCOnsulta
				} else {
					existingPaciente.agregarConsulta(consulta);
				}
			}
		}
	}
}
