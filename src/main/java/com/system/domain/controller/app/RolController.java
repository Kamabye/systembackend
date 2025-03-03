package com.system.domain.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.system.domain.model.postgresql.Rol;
import com.system.domain.service.interfaces.IRolService;

@RestController
@RequestMapping({ "apiv1/rol", "apiv1/rol/" })
@CrossOrigin(origins = { "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/", "https://kamabyeapp.onrender.com", "https://kamabyeapp.onrender.com/", "http://localhost:4200", "http://localhost:8080", "http://localhost:4200/", "http://localhost:8080/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class RolController {
	
	@Autowired
	private IRolService rolService;
	
	@PreAuthorize("hasAnyRole('Administrador','ROLE_Administrador')")
	@GetMapping("")
	public ResponseEntity<?> roles() {
		Map<String, Object> responseBody = new HashMap<>();
		List<Rol> listaRoles;
		
		try {
			
			listaRoles = rolService.findAll();
			
			if (!listaRoles.isEmpty()) {
				
				return new ResponseEntity<List<Rol>>(listaRoles, null, HttpStatus.OK);
			}
			
			responseBody.put("error", "No se encontraron resultados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (EmptyResultDataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@PreAuthorize("hasAnyRole('Administrador','ROLE_Administrador')")
	@GetMapping("{idRol}")
	public ResponseEntity<?> findRol(@PathVariable(name = "idRol", required = false) String idRolString) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			if (idRolString != null) {
				
				Long idRol = Long.valueOf(idRolString);
				
				Rol rol = rolService.findById(idRol);
				
				if (rol != null) {
					System.out.println(rol.toString());
					return new ResponseEntity<Rol>(rol, null, HttpStatus.OK);
				}
				
				responseBody.put("error",
				  "El Objeto con ID: ".concat(idRolString.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("mensaje", "Parámetros inválidos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@PreAuthorize("hasAnyRole('Administrador','ROLE_Administrador')")
	@PostMapping("")
	public ResponseEntity<?> saveRol(@RequestBody Rol rol) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			if (rol != null) {
				
				Rol rolSave = rolService.save(rol);
				
				if (rolSave != null) {
					
					return new ResponseEntity<Rol>(rolSave, null, HttpStatus.OK);
					
				}
				responseBody.put("error",
				  "El Objeto :".concat(rol.toString().concat(" no se pudo guardar en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			
			responseBody.put("error", "Objeto null");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataIntegrityViolationException e) {
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@PreAuthorize("hasAnyRole('Administrador','ROLE_Administrador')")
	@PutMapping("{idRol}")
	public ResponseEntity<?> updateRol(@PathVariable(name = "idRol", required = true) String idRolString,
	  @RequestBody Rol rol) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			if (idRolString != null && rol != null) {
				
				Long idRol = Long.valueOf(idRolString);
				
				Rol rolTemp = rolService.findById(idRol);
				
				if (rolTemp != null) {
					
					if (rol.getRol() != "") {
						rolTemp.setRol(rol.getRol());
					}
					
					Rol rolUpdate = rolService.save(rolTemp);
					
					if (rolUpdate != null) {
						
						return new ResponseEntity<Rol>(rolUpdate, null, HttpStatus.OK);
						
					}
					responseBody.put("error", "El ID: "
					  .concat(rol.toString().concat(" no se pudo actualizar en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null,
					  HttpStatus.INTERNAL_SERVER_ERROR);
					
				}
				
				responseBody.put("error",
				  "El ID: ".concat(rol.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("error", "Datos inválidos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@PreAuthorize("hasAnyRole('Administrador','ROLE_Administrador')")
	@DeleteMapping("{idRol}")
	public ResponseEntity<?> deleteRolByIDUrl(@PathVariable(name = "idRol", required = true) String idRolString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Long idRol = Long.valueOf(idRolString);
			
			Rol rolDeleted = rolService.deleteReturn(idRol);
			
			if (rolDeleted != null) {
				return new ResponseEntity<Rol>(rolDeleted, null, HttpStatus.OK);
			}
			
			responseBody.put("mensaje", "El ID: " + idRol + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (EmptyResultDataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
}
