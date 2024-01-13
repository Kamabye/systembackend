package com.domain.system.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({ "apiv1/mercadopago", "apiv1/mercadopago/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200" }, methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class MercadoPagoController {
	
	@GetMapping("")
	public String payment() {
		
		return "Mercado Pago";
	}

}
