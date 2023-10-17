package com.domain.system.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apiv1")
@CrossOrigin(origins = "http://localhost:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")
public class ApiController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping
	public String index() {
		return "Bienvenido a la API de SystemApp sin slash";
	}

	@GetMapping("/")
	public String slash() {
		return "Bienvenido a la API de SystemApp con slash";
	}

	@GetMapping("/demo")
	public String demo() {
		return "Bienvenido al demo de la API SystemAppx2";
	}

	@GetMapping("/demo/")
	public String demoslash() {
		return "Bienvenido al demo con slash de la API SystemApp con slash";
	}

	@GetMapping("/authentication")
	public ResponseEntity<?> authentication() {
		Map<String, Object> response = new HashMap<>();
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken("administrador@domain.comm", "12345"));
			response.put("mensaje", "Authenticatión realizada con éxito");
			response.put("UserDetails", authentication.toString());
			response.put("Usuario", authentication.getPrincipal().toString());
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos DataAccessException");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (BadCredentialsException e) {
			response.put("mensaje", "Credenciales incorrectas BadCredentialsException");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos de tipo Exception");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			response.put("errorCompleto", e.toString());
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
