package com.system.domain.controllers;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.system.domain.interfaces.IConsultaService;
import com.system.domain.interfaces.IPacienteService;
import com.system.domain.models.postgresql.Consulta;
import com.system.domain.models.postgresql.Paciente;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping({ "apiv1/optica", "apiv1/optica/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200", "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders =  { "Authorization", "Content-Type" }, exposedHeaders = {})
public class OpticaController {
	
	@Autowired
	IPacienteService pacienteService;
	
	@Autowired
	IConsultaService consultaService;
	
	// @PreAuthorize("hasAnyRole('Administrador','USERS_Administrador') OR #id ==
	// authentication.principal.id")
	// @PreAuthorize("hasAnyRole('Administrador','USERS_Administrador')")
	@GetMapping("pacientes")
	public ResponseEntity<?> getAllPacientes(@RequestParam(defaultValue = "0") Integer pageNumber,
	  @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize, @RequestParam(name = "string", required = false, defaultValue = "") String string) {
		Map<String, Object> responseBody = new HashMap<>();
		
//List<Usuario> listaUsuarios;
		Page<Paciente> pageListaPacientes;
		
		try {
			
			if (string != null && !string.isEmpty() && !string.isBlank()) {
				
				System.out.println("Búsqueda por string");
				
				pageListaPacientes = pacienteService.findByString(pageNumber, pageSize, string);
				
				if (!pageListaPacientes.isEmpty()) {
					return new ResponseEntity<Page<Paciente>>(pageListaPacientes, HttpStatus.OK);
				}
				responseBody.put("mensaje", "No se encontraron resultados que contengan: " + string);
				return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.NOT_FOUND);
			}
			
			// listaUsuarios = userService.findAll();
			// System.out.println("Busqueda paginada");
			System.out.println("Búsqueda paginada sin String");
			pageListaPacientes = pacienteService.findAll(pageNumber, pageSize);
			
			// if (!listaUsuarios.isEmpty()) {
			if (!pageListaPacientes.isEmpty()) {
				
				// return new ResponseEntity<List<Usuario>>(listaUsuarios, null, HttpStatus.OK);
				return new ResponseEntity<Page<Paciente>>(pageListaPacientes, HttpStatus.OK);
				
			}
			
			responseBody.put("mensaje", "No se encontraron resultados");
			return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.NOT_FOUND);
			
		} catch (ConstraintViolationException e) {
			responseBody.put("mensaje", "La cantidad de resultados por página debe ser mayor a 0");
			responseBody.put("error", "ConstraintViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMessage())));
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
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@GetMapping("pacientes/{idPaciente}")
	public ResponseEntity<?> getPacienteById(@PathVariable(name = "idPaciente", required = false) String idPacienteString) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			if (idPacienteString != null) {
				
				Long idPaciente = Long.valueOf(idPacienteString);
				
				Paciente paciente = pacienteService.findById(idPaciente);
				
				if (paciente != null) {
					return new ResponseEntity<Paciente>(paciente, null, HttpStatus.OK);
				}
				
				responseBody.put("mensaje",
				  "El ID: ".concat(idPacienteString.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("mensaje", "Parámetros nulos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error", "NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
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
	
	// @PostMapping(value = "", consumes = "application/json", produces =
	// "application/json")
	@PostMapping("pacientes")
	public ResponseEntity<?> createPaciente(@RequestBody Paciente paciente) {
		
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
	
	/**
	 * Los métodos UPDATE se deben definir si al mandar un valor null se guardará o
	 * se conservará el valor anterior, ya que se puede eliminar un dato para que no
	 * se muestre por decisión del usuario
	 * 
	 * @param paciente
	 * @return
	 */
	@PutMapping("pacientes")
	public ResponseEntity<?> totalUpdatePaciente(@RequestBody Paciente paciente) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			Long countUsuario = pacienteService.countById(paciente.getIdPaciente());
			
			if (countUsuario > 0) {
				
				Paciente userUpdate = pacienteService.putUpdate(paciente);
				
				if (userUpdate != null) {
					
					return new ResponseEntity<Paciente>(userUpdate, null, HttpStatus.OK);
					
				}
				responseBody.put("mensaje",
				  "El ID: ".concat(paciente.getIdPaciente().toString().concat(" no se pudo actualizar en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			
			responseBody.put("mensaje", "El ID: ".concat(paciente.toString().concat(" no existe en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		} catch (DataAccessException e) {
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
	
	/**
	 * Este método actualizará solo los valores presentes en los atributos. Los
	 * valores null dejarán los valores almacenados en la base de datos
	 * 
	 * @param paciente
	 * @return
	 */
	@PatchMapping("pacientes")
	public ResponseEntity<?> partialUpdatePaciente(@RequestBody Paciente paciente) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			Long countUsuario = pacienteService.countById(paciente.getIdPaciente());
			
			if (countUsuario > 0) {
				
				Paciente userUpdate = pacienteService.patchUpdate(paciente);
				
				if (userUpdate != null) {
					
					return new ResponseEntity<Paciente>(userUpdate, null, HttpStatus.OK);
					
				}
				responseBody.put("mensaje",
				  "El ID: ".concat(paciente.getIdPaciente().toString().concat(" no se pudo actualizar en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			
			responseBody.put("mensaje", "El ID: ".concat(paciente.toString().concat(" no existe en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		} catch (DataAccessException e) {
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
	
	@DeleteMapping("pacientes/{idPaciente}")
	public ResponseEntity<?> deletePacienteByIdURL(
	  @PathVariable(name = "idPaciente", required = true) String idPacienteString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Long idUsuario = Long.valueOf(idPacienteString);
			
			Paciente pacienteDeleted = pacienteService.deleteAndReturn(idUsuario);
			
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
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@PostMapping("load")
	public ResponseEntity<?> savePacientes(@RequestBody List<Paciente> pacientes) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			for (Paciente paciente : pacientes) {
				paciente.setIdPaciente(null);
				for (Consulta consulta : paciente.getConsultas()) {
					consulta.setIdConsulta(null);
					consulta.setPaciente(paciente);
				}
			}
			
			List<Paciente> pacienteSave = pacienteService.saveList(pacientes);
			
			return new ResponseEntity<List<Paciente>>(pacienteSave, null, HttpStatus.OK);
			
		} catch (DataIntegrityViolationException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El Objeto no existe: ");
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
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
	
	@PutMapping("update")
	public ResponseEntity<?> updatePacientes(@RequestBody List<Paciente> pacientes) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			List<Paciente> listPacientes = pacienteService.putUpdateList(pacientes);
			
			if (!listPacientes.isEmpty()) {
				
				return new ResponseEntity<List<Paciente>>(listPacientes, null, HttpStatus.OK);
				
			}
			responseBody.put("mensaje",
			  "El ID: ".concat(" no se pudo actualizar en la base de datos!."));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (DataAccessException e) {
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
	
	@PatchMapping("update")
	public ResponseEntity<?> patchPacientes(@RequestBody List<Paciente> pacientes) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			List<Paciente> listPacientes = pacienteService.patchUpdateList(pacientes);
			
			return new ResponseEntity<List<Paciente>>(listPacientes, null, HttpStatus.OK);
			
		} catch (DataAccessException e) {
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
	
	@GetMapping("consultas")
	public ResponseEntity<?> consultas(@RequestParam(defaultValue = "0") int pageNumber,
	  @RequestParam(defaultValue = "2") int pageSize, @RequestParam(name = "idConsulta", required = false) Integer idConsultaString) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			if (idConsultaString != null) {
				Long idConsulta = Long.valueOf(idConsultaString);
				
				if (idConsulta > 0) {
					Consulta consulta = consultaService.findByIdConsulta(idConsulta);
					
					if (consulta != null) {
						return new ResponseEntity<Consulta>(consulta, null, HttpStatus.OK);
					}
					
					responseBody.put("mensaje",
					  "El ID: ".concat(idConsultaString.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				}
			}
			// List<Usuario> listaUsuarios;
			Page<Consulta> pageListaConsultas;
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
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@GetMapping("consultas/{idPaciente}")
	public ResponseEntity<?> findConsultasByPaciente(@PathVariable(name = "idPaciente", required = false) String idPacienteString, @RequestParam(defaultValue = "0") int pageNumber,
	  @RequestParam(defaultValue = "10") int pageSize) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			if (idPacienteString != null) {
				
				Long idPaciente = Long.valueOf(idPacienteString);
				
				// Paciente paciente = pacienteService.findByIdPaciente(idPaciente);
				Page<Consulta> pageConsultas = consultaService.findByIdPaciente(idPaciente, pageNumber, pageSize);
				
				if (!pageConsultas.isEmpty()) {
					// return new ResponseEntity<Set<Consulta>>(paciente.getConsultas(), null,
					// HttpStatus.OK);
					return new ResponseEntity<Page<Consulta>>(pageConsultas, null, HttpStatus.OK);
				}
				
				responseBody.put("mensaje",
				  "El ID: ".concat(idPacienteString.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("mensaje", "Parámetros nulos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error", "NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
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
	
	@PostMapping("consultas")
	public ResponseEntity<?> saveConsulta(@RequestBody Consulta consulta) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			Consulta consultaSave = consultaService.save(consulta);
			
			if (consultaSave != null) {
				
				return new ResponseEntity<Consulta>(consultaSave, null, HttpStatus.OK);
				
			}
			responseBody.put("mensaje",
			  "El Objeto :".concat(consulta.toString().concat(" no se pudo guardar en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		} catch (DataIntegrityViolationException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El Objeto: ".concat(consulta.getIdConsulta().toString().concat(" ya existe en la base de datos")));
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
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
	
	@PutMapping("consultas")
	public ResponseEntity<?> updateConsulta(@RequestBody Consulta consulta) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			Long countConsulta = consultaService.countByIdConsulta(consulta.getIdConsulta());
			
			if (countConsulta > 0) {
				
				Consulta consultaUpdate = consultaService.putUpdate(consulta);
				
				if (consultaUpdate != null) {
					
					return new ResponseEntity<Consulta>(consultaUpdate, null, HttpStatus.OK);
					
				}
				responseBody.put("mensaje",
				  "El ID: ".concat(consulta.toString().concat(" no se pudo actualizar en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			
			responseBody.put("mensaje", "El ID: ".concat(consulta.toString().concat(" no existe en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		} catch (DataAccessException e) {
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
	
	@PatchMapping("consultas")
	public ResponseEntity<?> patchUpdateConsulta(@RequestBody Consulta consulta) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			Long countConsulta = consultaService.countByIdConsulta(consulta.getIdConsulta());
			
			if (countConsulta > 0) {
				
				Consulta consultaUpdate = consultaService.patchUpdate(consulta);
				
				if (consultaUpdate != null) {
					
					return new ResponseEntity<Consulta>(consultaUpdate, null, HttpStatus.OK);
					
				}
				responseBody.put("mensaje",
				  "El ID: ".concat(consulta.getIdConsulta().toString().concat(" no se pudo actualizar en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			
			responseBody.put("mensaje", "El ID: ".concat(consulta.getIdConsulta().toString().concat(" no existe en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			
		} catch (DataAccessException e) {
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
	
	@DeleteMapping("consultas/{idConsulta}")
	public ResponseEntity<?> deleteConsulta(
	  @PathVariable(name = "idConsulta", required = true) String idConsultaString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Long idConsulta = Long.valueOf(idConsultaString);
			
			Consulta consultaDeleted = consultaService.deleteReturn(idConsulta);
			
			if (consultaDeleted != null) {
				return new ResponseEntity<Consulta>(consultaDeleted, null, HttpStatus.OK);
			}
			
			responseBody.put("mensaje", "El ID: " + idConsulta + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error", "NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID : " + idConsultaString + " no existe en la base de datos");
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
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<?> handleAccessDeniedException() {
		return new ResponseEntity<>("Acceso Denegado: El usuario no tiene los permisos para este recurso", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
		String message = "Content-Type not supported: " + ex.getContentType();
		return new ResponseEntity<>(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
		// Build an error response with details about the violated constraint(s)
		String message = "Content-Type not supported: " + ex.getConstraintViolations().toString();
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
}
