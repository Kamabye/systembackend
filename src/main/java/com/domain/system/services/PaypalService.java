package com.domain.system.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Address;
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
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;

@Service
public class PaypalService {
	@Value("${website.url}")
	private String websiteUrl;

	@Autowired
	private APIContext context;

	@Autowired
	private PayPalHttpClient payPalHttpClient;

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

		RedirectUrls redirectUrls = new RedirectUrls();
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
		return payment.create(context);
	}

	/**
	 * Este método retorna un Payment pero solicita todos los datos para crearlo
	 * 
	 * @param subtotal
	 * @param divisa
	 * @param metodoPago
	 * @param intencion
	 * @param descripcion
	 * @param cancelUrl
	 * @param successUrl
	 * @param envio
	 * @param impuesto
	 * @param total
	 * @return
	 * @throws PayPalRESTException
	 */
	public Payment createPayment(BigDecimal subtotal, String divisa, String metodoPago, String intencion,
			String descripcion, String cancelUrl, String successUrl, BigDecimal envio, BigDecimal impuesto,
			BigDecimal total) throws PayPalRESTException {

		Amount amount = new Amount();
		// amount.setCurrency("USD");
		// amount.setCurrency("EUR");
		amount.setCurrency("MXN");
		// amount.setCurrency(divisa);
		amount.setTotal(total.toString());

		Details details = new Details();
		details.setSubtotal(subtotal.toString());
		details.setShipping(envio.toString());
		details.setTax(impuesto.toString());

		amount.setDetails(details);

		ItemList itemList = new ItemList();

		List<Item> items = new ArrayList<Item>();

		Item item1 = new Item();
		item1.setCurrency("MXN");
		item1.setName("Item1");
		item1.setSku("SKU1");
		item1.setPrice("100.00");
		item1.setTax("16.00");
		item1.setQuantity("1");

		items.add(item1);

		Item item2 = new Item();
		item2.setCurrency("MXN");
		item2.setName("Item2");
		item2.setSku("SKU2");
		item2.setPrice("100.00");
		item2.setTax("16.00");
		item2.setQuantity("1");

		items.add(item1);

		itemList.setItems(items);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("Pago de Orden de Compra en línea");
		// transaction.setDescription(descripcion);

		transaction.setItemList(itemList);

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// PayerInfo payerInfo = new PayerInfo();
		// payerInfo.setFirstName("Juan Carlos")
		// .setLastName("Hernández Vásquez")
		// .setEmail("juancarloshdzvqz@gmail.com");

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		// payer.setPaymentMethod(metodoPago);
		// payer.setPayerInfo(payerInfo);

		Payment payment = new Payment();
		payment.setIntent("sale");
		// payment.setIntent(intencion);

		// payment.setIntent("authorize");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(websiteUrl + "/apiv1/paypal/cancel");
		redirectUrls.setReturnUrl(websiteUrl + "/apiv1/paypal/success");

		payment.setRedirectUrls(redirectUrls);

		// System.out.println("Creando payent...");
		// System.out.println(payment.toJSON());
		return payment.create(context);
	}

	public String createPayment(BigDecimal fee) {
		OrderRequest orderRequest = new OrderRequest();

		orderRequest.checkoutPaymentIntent("CAPTURE");
		AmountWithBreakdown amountBreakdown = new AmountWithBreakdown().currencyCode("USD").value(fee.toString());
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().amountWithBreakdown(amountBreakdown);
		orderRequest.purchaseUnits(List.of(purchaseUnitRequest));
		ApplicationContext applicationContext = new ApplicationContext().returnUrl("https://localhost:4200/capture")
				.cancelUrl("https://localhost:4200/cancel");
		orderRequest.applicationContext(applicationContext);
		OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);

		try {
			HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
			Order order = orderHttpResponse.result();

			String redirectUrl = order.links().stream().filter(link -> "approve".equals(link.rel())).findFirst()
					.orElseThrow(NoSuchElementException::new).href();

			// return new PaymentOrder("success", order.id(), redirectUrl);
			return "success";
		} catch (IOException e) {
			// log.error(e.getMessage());
			// return new PaymentOrder("Error");
			return "Error";
		}

	}

	public String completePayment(String token) {
		OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
		try {
			HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
			if (httpResponse.result().status() != null) {
				// return new CompletedOrder("success", token);
				return "sucess";
			}
		} catch (IOException e) {
			// log.error(e.getMessage());
		}
		// return new CompletedOrder("error");
		return "error";
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {

		Payment payment = new Payment();
		payment.setId(paymentId);

		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		return payment.execute(context, paymentExecution);
	}

}
