package com.system.domain;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * CommandLineRunner ejecuta el metodo run
 */
public class SystemApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// MercadoPagoConfig.setAccessToken(System.getenv("clientToken"));
	}
	
}
