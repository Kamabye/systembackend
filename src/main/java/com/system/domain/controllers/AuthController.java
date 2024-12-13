package com.system.domain.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.domain.services.AuthenticationByJwtService;

@RestController
@RequestMapping({ "apiv1/auth", "apiv1/auth/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200" }, methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
		RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" })
public class AuthController {

	@Autowired
	AuthenticationByJwtService authService;

	@PostMapping("")
	public ResponseEntity<?> auth(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (username != null && password != null) {

				String token = authService.authenticate(username, password);

				if (token != null) {
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.TEXT_PLAIN);

					return new ResponseEntity<String>(token, headers, HttpStatus.OK);
				}

				response.put("mensaje", "No se pudo generar el JWT");
				return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "Credenciales inválidas");
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (DataAccessException e) {
			// e.printStackTrace();
			response.put("mensaje", "Ha ocurrido un error.");
			response.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadCredentialsException e) {
			response.put("mensaje", "Credenciales incorrectas BadCredentialsException");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.NOT_FOUND);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			response.put("mensaje", "Error en  la authtenticacion AuthenticationException");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}
}
