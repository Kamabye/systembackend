package com.system.domain.controllers;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.system.domain.models.dto.PaymentPayPalDTO;
import com.system.domain.models.dto.ProductoDTOPayPal;
import com.system.domain.services.PayPalServiceV2;
import com.system.domain.services.PaypalServiceV1;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping({ "apiv1/paypal", "apiv1/paypal/" })
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:4200", "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class PayPalController {
	
	@Autowired
	private PaypalServiceV1 paypalService;
	
	@Autowired
	private PayPalServiceV2 paypalServicev2;
	
	@PostMapping("/create-order")
	public Mono<?> createOrder(@RequestParam double amount, @RequestParam String currency, @RequestParam String return_url) {
		
		Map<String, Object> responseBody = new HashMap<>();
		try {
			return paypalServicev2.createOrder(amount, currency, return_url)
			  .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order))
			  .onErrorResume(e -> {
      //logger.error("Error en el controlador al crear la orden", e);
      return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  })
			  ;
		}
		catch (Exception e){
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			
			return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
		}
		
	}
	
	@PostMapping("/capture-order")
	public Mono<?> captureOrder(@RequestParam String orderId) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			return paypalServicev2.captureOrder(orderId)
			  .map(status -> ResponseEntity.ok(status))
			  .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
		}
		catch (Exception e){
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			
			return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
		}
	}
	
	@PostMapping("payExample")
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
			
			BigDecimal total = new BigDecimal("2420.00");
			String subTotal = "2000.00";
			String impuestoTotal = "320.00";
			String envio = "100.00";
			
			Payment payment = paypalService.createPayment("sale", "paypal", "juancarloshdzvqz@gmail.com", "MXN", total.toString(),
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
	
	@PostMapping("payment")
	public ResponseEntity<?> createPayment(@RequestBody PaymentPayPalDTO paymentPayPalDTO) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			String urlPagoPayPal = "";
			
			Payment payment = paypalService.createPayment(paymentPayPalDTO);
			
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					urlPagoPayPal = link.getHref();
				}
			}
			System.out.println("pagoCreadoConéxito");
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
	
	@GetMapping("success")
	public ResponseEntity<?> success(@RequestParam(name = "paymentId", required = true) String paymentId,
	  @RequestParam(name = "PayerID", required = true) String payerId) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			
			if ("approved".equals(payment.getState())) {
				return new ResponseEntity<String>("Pago exitoso", null, HttpStatus.OK);
			}
			return new ResponseEntity<String>("Pago no exitoso", null, HttpStatus.NO_CONTENT);
		} catch (PayPalRESTException e) {
			responseBody.put("mensaje", "Ha ocurrido un error PayPalRESTException.");
			responseBody.put("error", "PayPalRESTException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error Exception.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@GetMapping("cancel")
	public ResponseEntity<?> cancel() {
		return new ResponseEntity<String>("Pago cancelado", null, HttpStatus.OK);
	}
	
	@PostMapping("webhook")
	public ResponseEntity<?> handleWebhook(@RequestBody String payload, HttpServletRequest request) throws IOException {
		
		// 1. Obtener el cuerpo de la solicitud (payload)
		System.out.println("Payload recibido:\n" + payload);
		
		// 2. Obtener las cabeceras de la solicitud
		System.out.println("Cabeceras:");
		request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
			System.out.println(headerName + ": " + request.getHeader(headerName));
		});
		
		// 3. (CRUCIAL) Verificar la firma del Webhook (ver el siguiente punto)
		if (verifyWebhookSignature(payload, request)) {
			// Procesar el evento del Webhook
			System.out.println("Webhook verificado. Procesando evento...");
			// Aquí va tu lógica para procesar el evento (ej. actualizar la base de datos)
			return new ResponseEntity<>("Webhook recibido y procesado", HttpStatus.OK);
		} else {
			System.err.println("Error: Firma del Webhook no válida.");
			return new ResponseEntity<>("Error: Firma no válida", HttpStatus.BAD_REQUEST);
		}
	}
	
	private boolean verifyWebhookSignature(String payload, HttpServletRequest request) throws IOException {
		// Implementar la lógica de verificación de la firma (ver el siguiente punto)
		// Esta es la parte MÁS IMPORTANTE para la seguridad
		// Por ahora, para pruebas, retornamos true
		return true; // **REEMPLAZAR CON LA VERIFICACIÓN REAL**
	}
	
}
