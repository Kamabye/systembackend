package com.domain.system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domain.system.interfaces.IRolService;
import com.domain.system.models.postgresql.Rol;

@RestController
@RequestMapping({ "apiv1/rol", "apiv1/rol/" })
@CrossOrigin(origins = {"http://localhost:8081","http://localhost:4200"}, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")
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

				//responseBody.put("mensaje", "Usuarios encontrados");
				//responseBody.put("usuarios", listaUsuarios);
				
				//return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
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

}
