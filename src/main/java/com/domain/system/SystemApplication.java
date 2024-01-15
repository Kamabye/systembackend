package com.domain.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mercadopago.MercadoPagoConfig;

@SpringBootApplication
public class SystemApplication implements CommandLineRunner {
	
	@Value("${mercadopago.clientToken}")
	private String clientToken;

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MercadoPagoConfig.setAccessToken(System.getenv("clientToken"));
	}

}
