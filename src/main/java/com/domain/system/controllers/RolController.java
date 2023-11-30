package com.domain.system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
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

import com.domain.system.interfaces.IRolService;
import com.domain.system.models.postgresql.Rol;

@RestController
@RequestMapping({ "apiv1/rol", "apiv1/rol/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200" }, methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE,
		RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" })
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

				// responseBody.put("mensaje", "Usuarios encontrados");
				// responseBody.put("usuarios", listaUsuarios);

				// return new ResponseEntity<Map<String, Object>>(responseBody, null,
				// HttpStatus.OK);

				HttpHeaders headers = new HttpHeaders();

				return new ResponseEntity<List<Rol>>(listaRoles, null, HttpStatus.OK);
			}

			responseBody.put("error", "Roles no encontrados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);

		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : "));
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
					// responseBody.put("obra", obra);
					return new ResponseEntity<Rol>(rol, null, HttpStatus.OK);

				}
				responseBody.put("mensaje",
						"El Rol ID: ".concat(idRolString.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}

			responseBody.put("error", "Roles no encontrados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@PostMapping("")
	public ResponseEntity<?> saveRol(@RequestBody Rol rol) {
		System.out.println("Se llamó al POST Rol");
		Map<String, Object> responseBody = new HashMap<>();

		try {

			if (rol != null) {

				Rol rolSave = rolService.save(rol);

				if (rolSave != null) {
					// responseBody.put("obra", obra);
					return new ResponseEntity<Rol>(rolSave, null, HttpStatus.OK);

				}
				responseBody.put("mensaje",
						"El Rol ID: ".concat(rol.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}

			responseBody.put("error", "Roles no encontrados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@DeleteMapping("{idRol}")
	public ResponseEntity<?> deleteObraByIDUrl(@PathVariable(name = "idRol", required = true) String idRolString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Long idRol = Long.valueOf(idRolString);

			Rol rolDelete = rolService.findById(idRol);
			if (rolDelete != null) {

				rolService.delete(idRol);

				// responseBody.put("mensaje", "La obra : " + idRol + " ha sido eliminada con
				// éxito");
				// responseBody.put("obraDTO", rolDelete);
				return new ResponseEntity<Rol>(rolDelete, null, HttpStatus.OK);
			}
			responseBody.put("mensaje", "La obra : " + idRol + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

	@PutMapping("{idRol}")
	public ResponseEntity<?> updateRol(@PathVariable(name = "idRol", required = true) String idRolString,
			@RequestBody Rol rol) {
		System.out.println("Se llamó al PUT Rol");
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
						// responseBody.put("obra", obra);
						return new ResponseEntity<Rol>(rolUpdate, null, HttpStatus.OK);

					}
					responseBody.put("mensaje",
							"El Rol ID: ".concat(rol.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

				}

				responseBody.put("mensaje",
						"El Rol ID: ".concat(rol.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}

			responseBody.put("error", "Datos inválidos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

}
