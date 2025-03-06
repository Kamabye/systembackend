package com.system.domain.controller.app;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	Map<String, Object> responseBody = new HashMap<>();
	
	@ExceptionHandler(DataAccessException.class) // Maneja excepciones 404 (Not Found)
	public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
		e.printStackTrace();
		responseBody.put("error", "DataAccessException: " + e.getMessage() + " => Causa: " + e.getMostSpecificCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(LockedException.class) // Maneja excepciones 404 (Not Found)
	public ResponseEntity<?> handleLockedException(LockedException e) {
		e.printStackTrace();
		responseBody.put("error", "LockedException: " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AuthenticationException.class) // Maneja excepciones 404 (Not Found)
	public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
		e.printStackTrace();
		responseBody.put("error", "AuthenticationException: " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class) // Maneja excepciones 404 (Not Found)
	public ResponseEntity<?> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
		e.printStackTrace();
		responseBody.put("error", "InvalidDataAccessApiUsageException: " + e.getMessage() + " => Causa: " + e.getMostSpecificCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HibernateException.class) // Maneja excepciones 404 (Not Found)
	public ResponseEntity<?> handleHibernateException(HibernateException e) {
		e.printStackTrace();
		responseBody.put("error", "HibernateException: " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(JsonMappingException.class) // Maneja excepciones 404 (Not Found)
	public ResponseEntity<?> handleJsonMappingException(JsonMappingException e) {
		e.printStackTrace();
		responseBody.put("error", "JsonMappingException: " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HttpMessageNotWritableException.class)
	public ResponseEntity<?> handleHttpMessageNotWritableException(HttpMessageNotWritableException e) {
		e.printStackTrace();
		responseBody.put("error", "HttpMessageNotWritableException " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class) // Maneja excepciones 404 (Not Found)
	public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
		e.printStackTrace();
		responseBody.put("error", "NoHandlerFoundException: " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
		e.printStackTrace();
		responseBody.put("error", "AccessDeniedException: " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
		e.printStackTrace();
		responseBody.put("error", "HttpMediaTypeNotSupportedException Content-Type not supported: " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<?> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
		e.printStackTrace();
		responseBody.put("error", "HttpMediaTypeNotAcceptableException: " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
//Otras excepciones que quieras manejar globalmente
	@ExceptionHandler(Exception.class) // Maneja cualquier otra excepción no manejada específicamente
	public ResponseEntity<?> handleException(Exception e) {
		e.printStackTrace();
		responseBody.put("error", "Exception : " + e.getMessage() + " => Causa: " + e.getCause());
		return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}