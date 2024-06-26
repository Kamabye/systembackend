package com.herokuapp.kamabye.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
@CrossOrigin(origins = "http://localhost:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")
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