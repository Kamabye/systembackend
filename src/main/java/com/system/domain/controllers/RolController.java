package com.system.domain.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.system.domain.interfaces.IRolService;
import com.system.domain.models.postgresql.Rol;

@RestController
@RequestMapping({ "apiv1/rol", "apiv1/rol/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200" }, methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, allowedHeaders = { "Authorization", "Content-Type" })
public class RolController {

	@Autowired
	private IRolService rolService;

	@GetMapping("")
	public ResponseEntity<?> roles() {
		Map<String, Object> responseBody = new HashMap<>();
		List<Rol> listaRoles;

		try {

			listaRoles = rolService.findAll();

			if (!listaRoles.isEmpty()) {

				return new ResponseEntity<List<Rol>>(listaRoles, null, HttpStatus.OK);
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
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

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

				responseBody.put("mensaje",
						"El Objeto con ID: ".concat(idRolString.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}
			responseBody.put("mensaje", "Parámetros inválidos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@PostMapping("")
	public ResponseEntity<?> saveRol(@RequestBody Rol rol) {
		Map<String, Object> responseBody = new HashMap<>();

		try {

			if (rol != null) {

				Rol rolSave = rolService.save(rol);

				if (rolSave != null) {

					return new ResponseEntity<Rol>(rolSave, null, HttpStatus.OK);

				}
				responseBody.put("mensaje",
						"El Objeto :".concat(rol.toString().concat(" no se pudo guardar en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}

			responseBody.put("mensaje", "Objeto null");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataIntegrityViolationException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El rol: ".concat(rol.getRol().concat(" ya existe en la base de datos")));
			responseBody.put("error", "DataIntegrityViolationException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

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
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID : " + idRolString + " no existe en la base de datos");
			responseBody.put("error", "EmptyResultDataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

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
					responseBody.put("mensaje", "El ID: "
							.concat(rol.toString().concat(" no se pudo actualizar en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null,
							HttpStatus.INTERNAL_SERVER_ERROR);

				}

				responseBody.put("mensaje",
						"El ID: ".concat(rol.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}
			responseBody.put("mensaje", "Datos inválidos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

}
