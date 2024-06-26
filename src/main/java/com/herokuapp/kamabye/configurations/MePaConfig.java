package com.herokuapp.kamabye.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MePaConfig {

	@Value("${mercadopago.clientId}")
	private String clientId;

	@Value("${mercadopago.clientSecret}")
	private String clientSecret;

	@Value("${mercadopago.clientToken}")
	private String clientToken;

}