package com.system.domain.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200", "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders =  { "Authorization", "Content-Type" }, exposedHeaders = {})
public class DemoController {

	@GetMapping
	public String index() {
		

		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("mensaje", "Usuarios encontrados");
		return "Bienvenido a la API de SystemApp sin slash";
	}

	@GetMapping("/")
	public String slash() {
		return "Bienvenido a la API de SystemApp con slash";
	}

}