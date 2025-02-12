package com.system.domain.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class KamabyeController {
	
	@ExceptionHandler(NoHandlerFoundException.class) // Maneja excepciones 404 (Not Found)
	public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("mensaje", "Recurso end point no encontrado: " + HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
 public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("error", "AccessDeniedException: ".concat(e.getMessage()));
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.UNAUTHORIZED);
 }
	
	// Otras excepciones que quieras manejar globalmente
	@ExceptionHandler(Exception.class) // Maneja cualquier otra excepción no manejada específicamente
	public ResponseEntity<?> handleGeneralException(Exception ex) {
		
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("mensaje", "Error interno del servidor: " + HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
		String message = "Content-Type not supported: " + ex.getContentType();
		return new ResponseEntity<>(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
}