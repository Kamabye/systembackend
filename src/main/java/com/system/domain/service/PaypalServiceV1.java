package com.system.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.system.domain.model.dto.PaymentPayPalDTO;
import com.system.domain.model.dto.ProductoDTOPayPal;

@Service
public class PaypalServiceV1 {
	
	@Value("${paypal.mode}")
	private String mode;
	
	@Value("${website.urldev}")
	private String websitedevUrl;
	
	@Value("${website.urlprod}")
	private String websiteprodUrl;
	
	
	@Autowired
	private APIContext apiContext;
	
	/**
	 * Este método retorna un Payment creado con datos por defecto
	 * 
	 * @return
	 * @throws PayPalRESTException
	 */
	public Payment createPayment() throws PayPalRESTException {
		
		Payment payment = new Payment();
		
		// payment.setBillingAgreementTokens(null);
		// payment.setCart("");
		// payment.setCreateTime("");
		// payment.setCreditFinancingOffered(null);
		// payment.setExperienceProfileId("");
		// payment.setFailedTransactions(null);
		
		// Código de motivo de error devuelto cuando el pago falló por algún motivo
		// válido.
		// payment.setFailureReason("");
		
		// Identificador del recurso de pago creado.
		// payment.setId("");
		
		// Intención de Pago "sale" pago inmediato
		payment.setIntent("sale");
		
		// payment.setLinks(null);
		
		// campo de formato libre para que los clientes lo utilicen para pasar un
		// mensaje al pagador
		// payment.setNoteToPayer("");
		
		// payment.setPayee(null);
		
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setEmail("");
		
		// Address address = new Address();
		// address.setPostalCode("");
		
		// payerInfo.setBillingAddress(address);
		
		payer.setPayerInfo(payerInfo);
		
		// Fuente de los fondos para este pago representada por una cuenta PayPal o una
		// tarjeta de crédito directa.
		payment.setPayer(payer);
		
		// payment.setPaymentInstruction(null);
		// payment.setPotentialPayerInfo(null);
		
		// Validar que las URL sean relativas o absolutas para desarrollo o produccion
		RedirectUrls redirectUrls = new RedirectUrls();
		
		String websiteUrl = (mode.equals("sandbox")) ? websitedevUrl : websiteprodUrl;
		redirectUrls.setCancelUrl(websiteUrl + "/apiv1/paypal/cancel");
		redirectUrls.setReturnUrl(websiteUrl + "/apiv1/paypal/success");
		
		// Conjunto de URL de redireccionamiento que usted proporciona solo para pagos
		// basados ​​en PayPal.
		payment.setRedirectUrls(redirectUrls);
		
		// El estado de la transacción de pago, autorización o pedido. created,
		// approved, failed
		// payment.setState("");
		
		List<Transaction> transacciones = new ArrayList<Transaction>();
		
		Transaction transaccion1 = new Transaction();
		
		Amount montoPago = new Amount();
		montoPago.setCurrency("MXN");
		montoPago.setTotal("300.00");
		
		Details detallesMontoPago = new Details();
		detallesMontoPago.setSubtotal("200.00");
		detallesMontoPago.setTax("32.00");
		detallesMontoPago.setShipping("68.00");
		
		montoPago.setDetails(detallesMontoPago);
		
		transaccion1.setAmount(montoPago);
		transaccion1.setDescription("Transaccion 1");
		
		ItemList itemList = new ItemList();
		List<Item> listaProductos = new ArrayList<Item>();
		
		Item producto1 = new Item();
		producto1.setCurrency("MXN").setName("Producto_1").setSku("SKU_1").setPrice("100.00").setTax("16.00")
		  .setQuantity("1").setDescription("Descripción del Producto_1");
		Item producto2 = new Item();
		producto2.setCurrency("MXN").setName("Producto_2").setSku("SKU_2").setPrice("100.00").setTax("16.00")
		  .setQuantity("1").setDescription("Descripción del Producto_2");
		
		listaProductos.add(producto1);
		listaProductos.add(producto2);
		
		itemList.setItems(listaProductos);
		
		transaccion1.setItemList(itemList);
		
		transacciones.add(transaccion1);
		
		// Detalles transaccionales, incluido el monto y los detalles del artículo.
		payment.setTransactions(transacciones);
		
		// payment.setUpdateTime(websiteUrl)
		
		System.out.println(payment.toString());
		return payment.create(apiContext);
	}
	
	/**
	 * Este método retorna un Payment con datos enviados al método
	 * 
	 * @param intencion         Sale o Authorize
	 * @param metodoPago        Paypal o tarjeta de crédito
	 * @param email
	 * @param divisa
	 * @param total
	 * @param subTotal
	 * @param impuestoTotal
	 * @param envio
	 * @param descripcionPago
	 * @param listaProductosDTO
	 * @return
	 * @throws PayPalRESTException
	 */
	public Payment createPayment(String intencion, String metodoPago, String email, String divisa, String total,
	  String subTotal, String impuestoTotal, String envio, String descripcionPago,
	  List<ProductoDTOPayPal> listaProductosDTO) throws PayPalRESTException {
		
		Payment payment = new Payment();
		
		// payment.setBillingAgreementTokens(null);
		// payment.setCart("");
		// payment.setCreateTime("");
		// payment.setCreditFinancingOffered(null);
		// payment.setExperienceProfileId("");
		// payment.setFailedTransactions(null);
		
		// Código de motivo de error devuelto cuando el pago falló por algún motivo
		// válido.
		// payment.setFailureReason("");
		
		// Identificador del recurso de pago creado.
		// payment.setId("");
		
		// Intención de Pago "sale" pago inmediato
		payment.setIntent(intencion);
		
		// payment.setLinks(null);
		
		// campo de formato libre para que los clientes lo utilicen para pasar un
		// mensaje al pagador
		// payment.setNoteToPayer("");
		
		// payment.setPayee(null);
		
		Payer payer = new Payer();
		payer.setPaymentMethod(metodoPago);
		
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setEmail(email);
		
		// Address address = new Address();
		// address.setPostalCode("");
		
		// payerInfo.setBillingAddress(address);
		
		payer.setPayerInfo(payerInfo);
		
		// Fuente de los fondos para este pago representada por una cuenta PayPal o una
		// tarjeta de crédito directa.
		payment.setPayer(payer);
		
		// payment.setPaymentInstruction(null);
		// payment.setPotentialPayerInfo(null);
		
		// Validar que las URL sean relativas o absolutas para desarrollo o produccion
		RedirectUrls redirectUrls = new RedirectUrls();
		String websiteUrl = (mode.equals("sandbox")) ? websitedevUrl : websiteprodUrl;
		redirectUrls.setCancelUrl(websiteUrl + "/apiv1/paypal/cancel");
		redirectUrls.setReturnUrl(websiteUrl + "/apiv1/paypal/success");
		
		// Conjunto de URL de redireccionamiento que usted proporciona solo para pagos
		// basados ​​en PayPal.
		payment.setRedirectUrls(redirectUrls);
		
		// El estado de la transacción de pago, autorización o pedido. created,
		// approved, failed
		// payment.setState("");
		
		List<Transaction> transacciones = new ArrayList<Transaction>();
		
		Amount montoPago = new Amount();
		montoPago.setCurrency(divisa);
		montoPago.setTotal(total.toString());
		
		Details detallesMontoPago = new Details();
		detallesMontoPago.setSubtotal(subTotal);
		detallesMontoPago.setTax(impuestoTotal);
		detallesMontoPago.setShipping(envio);
		
		montoPago.setDetails(detallesMontoPago);
		
		Transaction transaccion1 = new Transaction();
		transaccion1.setDescription(descripcionPago);
		transaccion1.setAmount(montoPago);
		
		ItemList itemList = new ItemList();
		List<Item> listaProductos = new ArrayList<Item>();
		
		for (ProductoDTOPayPal productoDTOPayPal : listaProductosDTO) {
			
			Item item = new Item();
			item.setCurrency(divisa).setName(productoDTOPayPal.getNombre()).setSku(productoDTOPayPal.getSku())
			  .setPrice(productoDTOPayPal.getPrecio()).setTax(productoDTOPayPal.getImpuesto())
			  .setQuantity(productoDTOPayPal.getCantidad()).setDescription(productoDTOPayPal.getDescripcion())
			  .setCategory(productoDTOPayPal.getCategoria());
			
			listaProductos.add(item);
			
		}
		
		itemList.setItems(listaProductos);
		
		transaccion1.setItemList(itemList);
		
		transacciones.add(transaccion1);
		
		// Detalles transaccionales, incluido el monto y los detalles del artículo.
		payment.setTransactions(transacciones);
		
		// payment.setUpdateTime(websiteUrl)
		
		System.out.println(payment.toString());
		return payment.create(apiContext);
	}
	
	public Payment createPayment(PaymentPayPalDTO paymentPayPalDTO) throws PayPalRESTException {
		Payment payment = new Payment();
		
		payment.setIntent(paymentPayPalDTO.getIntencion());
		
		Payer payer = new Payer();
		payer.setPaymentMethod(paymentPayPalDTO.getMetodoPago());
		
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setEmail(paymentPayPalDTO.getEmail());
		
		payer.setPayerInfo(payerInfo);
		
		payment.setPayer(payer);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(paymentPayPalDTO.getCancelUrl());
		redirectUrls.setReturnUrl(paymentPayPalDTO.getReturnUrl());
		
		payment.setRedirectUrls(redirectUrls);
		
		List<Transaction> transacciones = new ArrayList<Transaction>();
		
		Amount montoPago = new Amount();
		montoPago.setCurrency(paymentPayPalDTO.getDivisa());
		montoPago.setTotal(paymentPayPalDTO.getTotal());
		
		Details detallesMontoPago = new Details();
		detallesMontoPago.setSubtotal(paymentPayPalDTO.getSubTotal());
		detallesMontoPago.setTax(paymentPayPalDTO.getImpuestoTotal());
		detallesMontoPago.setShipping(paymentPayPalDTO.getEnvio());
		
		montoPago.setDetails(detallesMontoPago);
		
		Transaction transaccion1 = new Transaction();
		transaccion1.setDescription(paymentPayPalDTO.getDescripcionPago());
		transaccion1.setAmount(montoPago);
		
		ItemList itemList = new ItemList();
		List<Item> listaProductos = new ArrayList<Item>();
		
		for (ProductoDTOPayPal productoDTOPayPal : paymentPayPalDTO.getListaProductosDTO()) {
			
			Item item = new Item();
			item.setCurrency(paymentPayPalDTO.getDivisa()).setName(productoDTOPayPal.getNombre()).setSku(productoDTOPayPal.getSku())
			  .setPrice(productoDTOPayPal.getPrecio()).setTax(productoDTOPayPal.getImpuesto())
			  .setQuantity(productoDTOPayPal.getCantidad()).setDescription(productoDTOPayPal.getDescripcion())
			  .setCategory(productoDTOPayPal.getCategoria());
			
			listaProductos.add(item);
			
		}
		
		itemList.setItems(listaProductos);
		
		transaccion1.setItemList(itemList);
		
		transacciones.add(transaccion1);
		
		payment.setTransactions(transacciones);
		
		System.out.println(payment.toString());
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		
		Payment payment = new Payment();
		payment.setId(paymentId);
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		
		return payment.execute(apiContext, paymentExecution);
	}
	
}
