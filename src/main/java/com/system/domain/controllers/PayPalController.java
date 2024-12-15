package com.system.domain.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.system.domain.models.dto.ProductoDTOPayPal;
import com.system.domain.services.PaypalService;

@RestController
@RequestMapping({ "apiv1/paypal", "apiv1/paypal/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200", "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders =  { "Authorization", "Content-Type" }, exposedHeaders = {})
public class PayPalController {

	@Autowired
	private PaypalService paypalService;

	@PostMapping("payment")
	public ResponseEntity<?> createPayment() {

		Map<String, Object> responseBody = new HashMap<>();

		try {

			String urlPagoPayPal = "";

			List<ProductoDTOPayPal> listaProductos = new ArrayList<ProductoDTOPayPal>();

			ProductoDTOPayPal producto1 = new ProductoDTOPayPal();
			producto1.setNombre("Producto_1");
			producto1.setSku("SKU_1");
			producto1.setDescripcion("Descripción Producto_1");
			producto1.setCategoria("DIGITAL");
			producto1.setPrecio("100");
			producto1.setImpuesto("16.00");
			producto1.setCantidad("10");

			listaProductos.add(producto1);

			ProductoDTOPayPal producto2 = new ProductoDTOPayPal();
			producto2.setNombre("Producto_2");
			producto2.setSku("SKU_2");
			producto2.setDescripcion("Descripción Producto_2");
			producto2.setCategoria("DIGITAL");
			producto2.setPrecio("100.00");
			producto2.setImpuesto("16.00");
			producto2.setCantidad("10");

			listaProductos.add(producto2);

			String total = "2420.00";
			String subTotal = "2000.00";
			String impuestoTotal = "320.00";
			String envio = "100.00";

			Payment payment = paypalService.createPayment("sale", "paypal", "juancarloshdzvqz@gmail.com", "MXN", total,
					subTotal, impuestoTotal, envio, "Pago de una partitura de nombre : ", listaProductos);

			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					urlPagoPayPal = link.getHref();
				}
			}
			return new ResponseEntity<String>(urlPagoPayPal, null, HttpStatus.OK);
		} catch (PayPalRESTException e) {
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "PayPalRESTException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@GetMapping("/success")
	public ResponseEntity<?> success(@RequestParam(name = "paymentId", required = true) String paymentId,
			@RequestParam(name = "token", required = false) String token,
			@RequestParam(name = "PayerID", required = true) String payerId) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);

			if ("approved".equals(payment.getState())) {
				return new ResponseEntity<String>("Pago exitoso", null, HttpStatus.OK);
			}
			return new ResponseEntity<String>("Pago no exitoso", null, HttpStatus.OK);
		} catch (PayPalRESTException e) {
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "PayPalRESTException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "Pago cancelado";
	}
}
