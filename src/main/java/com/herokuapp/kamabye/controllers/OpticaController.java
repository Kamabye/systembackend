package com.herokuapp.kamabye.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.herokuapp.kamabye.interfaces.IConsultaService;
import com.herokuapp.kamabye.interfaces.IPacienteService;
import com.herokuapp.kamabye.models.postgresql.Consulta;
import com.herokuapp.kamabye.models.postgresql.Paciente;

@RestController
@RequestMapping({ "apiv1/optica", "apiv1/optica/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200", "https://kamabye.herokuapp.com", "https://opticalemus.herokuapp.com" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class OpticaController {
	
	@Autowired
	IPacienteService pacienteService;
	
	@Autowired
	IConsultaService consultaService;
	
	@GetMapping("paciente")
	public ResponseEntity<?> pacientes(@RequestParam(defaultValue = "0") int pageNumber,
	  @RequestParam(defaultValue = "2") int pageSize) {
		Map<String, Object> responseBody = new HashMap<>();
		// List<Usuario> listaUsuarios;
		Page<Paciente> pageListaPacientes;
		
		try {
			
			// listaUsuarios = userService.findAll();
			pageListaPacientes = pacienteService.findAll(pageNumber, pageSize);
			
			// if (!listaUsuarios.isEmpty()) {
			if (!pageListaPacientes.isEmpty()) {
				
				// return new ResponseEntity<List<Usuario>>(listaUsuarios, null, HttpStatus.OK);
				return new ResponseEntity<Page<Paciente>>(pageListaPacientes, null, HttpStatus.OK);
				
			}
			
			responseBody.put("mensaje", "No se encontraron resultados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "No se encontraron resultados.");
			responseBody.put("error", "EmptyResultDataAccessException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error",
			  "DataAccessException: ".concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	
	// @PostMapping(value = "", consumes = "application/json", produces =
	// "application/json")
	@PostMapping("paciente")
	public ResponseEntity<?> savePaciente(@RequestBody Paciente paciente) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			Paciente pacienteSave = pacienteService.save(paciente);
			
			if (pacienteSave != null) {
				
				return new ResponseEntity<Paciente>(pacienteSave, null, HttpStatus.OK);
				
			}
			responseBody.put("mensaje",
			  "El Objeto :".concat(paciente.toString().concat(" no se pudo guardar en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		} catch (DataIntegrityViolationException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El Objeto: ".concat(paciente.getEmail().concat(" ya existe en la base de datos")));
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error",
			  "DataAccessException: ".concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@PutMapping("paciente")
	public ResponseEntity<?> updatePaciente(@RequestBody Paciente paciente) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			Long countUsuario = pacienteService.countByIdPaciente(paciente.getIdPaciente());
			
			if (countUsuario > 0) {
				
				Paciente userUpdate = pacienteService.update(paciente);
				
				if (userUpdate != null) {
					
					return new ResponseEntity<Paciente>(userUpdate, null, HttpStatus.OK);
					
				}
				responseBody.put("mensaje",
				  "El ID: ".concat(paciente.toString().concat(" no se pudo actualizar en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			
			responseBody.put("mensaje", "El ID: ".concat(paciente.toString().concat(" no existe en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		}catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error",
			  "DataAccessException: ".concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@DeleteMapping("paciente/{idPaciente}")
	public ResponseEntity<?> deletePacienteByPath(
	  @PathVariable(name = "idPaciente", required = true) String idPacienteString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Long idUsuario = Long.valueOf(idPacienteString);
			
			Paciente pacienteDeleted = pacienteService.deleteReturn(idUsuario);
			
			if (pacienteDeleted != null) {
				return new ResponseEntity<Paciente>(pacienteDeleted, null, HttpStatus.OK);
			}
			
			responseBody.put("mensaje", "El ID: " + idUsuario + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error", "NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID : " + idPacienteString + " no existe en la base de datos");
			responseBody.put("error", "EmptyResultDataAccessException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error",
			  "DataAccessException: ".concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@GetMapping("consulta")
	public ResponseEntity<?> consultas(@RequestParam(defaultValue = "0") int pageNumber,
	  @RequestParam(defaultValue = "2") int pageSize) {
		Map<String, Object> responseBody = new HashMap<>();
		// List<Usuario> listaUsuarios;
		Page<Consulta> pageListaConsultas;
		
		try {
			
			// listaUsuarios = userService.findAll();
			pageListaConsultas = consultaService.findAll(pageNumber, pageSize);
			
			// if (!listaUsuarios.isEmpty()) {
			if (!pageListaConsultas.isEmpty()) {
				
				// return new ResponseEntity<List<Usuario>>(listaUsuarios, null, HttpStatus.OK);
				return new ResponseEntity<Page<Consulta>>(pageListaConsultas, null, HttpStatus.OK);
				
			}
			
			responseBody.put("mensaje", "No se encontraron resultados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "No se encontraron resultados.");
			responseBody.put("error", "EmptyResultDataAccessException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error",
			  "DataAccessException: ".concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<String> handleAccessDeniedException() {
		return new ResponseEntity<>("Acceso Denegado: El usuario no tiene los permisos para este recurso", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
		String message = "Content-Type not supported: " + ex.getContentType();
		return new ResponseEntity<>(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
}
