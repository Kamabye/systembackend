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
	private String liveClientID;
	@Value("${paypal.client-secret}")
	private String liveClientSecret;
	@Value("${paypal.baseUrlLive}")
	private String baseUrlLive;
	
	@Value("${paypal.client-id.sandbox}")
	private String sandboxClientId;
	@Value("${paypal.client-secret.sandbox}")
	private String sandboxClientSecret;
	@Value("${paypal.baseUrlSandobox.sandbox}")
	private String baseUrlSandobox;
	
	@Value("${paypal.mode}")
	private String mode;
	
	@Bean
	public APIContext apiContextPaypal() {
		
		String clientId = (mode.equals("sandbox")) ? sandboxClientId : liveClientID;
		String clientSecret = (mode.equals("sandbox")) ? sandboxClientSecret : liveClientSecret;
		
		APIContext context = new APIContext(clientId, clientSecret, mode, paypalSdkConfig());
		
		return context;
		
	}
	
	@Bean
	public Map<String, String> paypalSdkConfig() {
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		return configMap;
	}
	
	@Bean
	public PayPalHttpClient getPaypalClient() {
		
		String clientId = (mode.equals("sandbox")) ? sandboxClientId : liveClientID;
		String clientSecret = (mode.equals("sandbox")) ? sandboxClientSecret : liveClientSecret;
		
		if (mode.equals("sandbox")) {
			return new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
		}
		return new PayPalHttpClient(new PayPalEnvironment.Live(clientId, clientSecret));
		
	}
	
}
