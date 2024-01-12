package com.domain.system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.domain.system.services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.exception.PayPalException;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping({ "apiv1/paypal", "apiv1/paypal/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200" }, methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class PayPalController {

	@Autowired
	private PaypalService paypalService;

	@PostMapping("payment")
	public String payment() throws PayPalException {

		Payment payment;
		try {
			payment = paypalService.createPayment();

			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					return "redirect:" + link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Redirigir al enlace de aprobación de PayPal

		return "redirect:/";
	}

	@GetMapping("/success")
	public String success(@RequestParam(name = "paymentId", required = true) String paymentId,
			@RequestParam(name = "token", required = false) String token,
			@RequestParam(name = "PayerID", required = true) String payerId) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);

			if ("approved".equals(payment.getState())) {
				// Pago exitoso, realiza acciones adicionales si es necesario
				return "Pago exitoso";
			} else {
				// Estado no aprobado, manejar según tus necesidades
				return "Estado no aprobado";
			}
		} catch (PayPalRESTException e) {
			// Manejar la excepción, según tus necesidades
			return "Error al ejecutar el pago";
		}
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "Pago cancelado";
	}
}
