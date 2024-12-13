package com.system.domain.configurations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

@Configuration
public class PaypalConfig {

	@Value("${paypal.client-id}")
	private String productionClientId;
	@Value("${paypal.client-secret}")
	private String productionClientSecret;

	@Value("${paypal.client-id.sandbox}")
	private String sandboxClientId;
	@Value("${paypal.client-secret.sandbox}")
	private String sandboxClientSecret;

	@Value("${paypal.mode}")
	private String mode;

	@Bean
	public Map<String, String> paypalSdkConfig() {
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		return configMap;
	}

	@Bean
	public APIContext apiContextPaypal() {

		String clientId = (mode.equals("sandbox")) ? sandboxClientId : productionClientId;
		String clientSecret = (mode.equals("sandbox")) ? sandboxClientSecret : productionClientSecret;

		APIContext context = new APIContext(clientId, clientSecret, mode, paypalSdkConfig());

		return context;

	}

	@Bean
	public PayPalHttpClient getPaypalClient() {
		
		String clientId = (mode.equals("sandbox")) ? sandboxClientId : productionClientId;
		String clientSecret = (mode.equals("sandbox")) ? sandboxClientSecret : productionClientSecret;
		
		return new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
	}

}
