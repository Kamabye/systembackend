package com.system.domain.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.system.domain.service.MePaService;

@RestController
@RequestMapping({ "apiv1/mercadopago", "apiv1/mercadopago/" })
@CrossOrigin(origins = { "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/", "https://kamabyeapp.onrender.com", "https://kamabyeapp.onrender.com/", "http://localhost:4200", "http://localhost:8080", "http://localhost:4200/", "http://localhost:8080/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class MercadoPagoController {

	@Autowired
	private MePaService mePagoService;

	@GetMapping("")
	public String createPayment() {

		try {
			Payment payment = mePagoService.createPayment();
			return payment.getCallbackUrl();
		} catch (MPApiException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return "Mercado Pago";
		} catch (MPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Mercado Pago";
		}

	}

}
