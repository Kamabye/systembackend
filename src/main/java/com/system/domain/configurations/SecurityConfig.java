package com.system.domain.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.system.domain.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	/**
	 * No se agrega system a la url porque lo agrega el contextpath del
	 * application.properties
	 */
	// "apiv1/user/**", "apiv1/rol/**",
	public static final String[] ENDPOINTS_WHITELIST = { "/demo/**", "/apiv1/auth/**", "/apiv1/obra/**",
	  "/apiv1/partitura/**", "/apiv1/pdf/**", "/apiv1/paypal/**", "/apiv1/shopcart/**", "/apiv1/optica/**", "/apiv1/user/**", "/apiv1/rol/**" };
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthorizationFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		  .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())
		    .contentSecurityPolicy(secPolicy -> secPolicy.policyDirectives("default-src 'self'; frame-ancestors https://system-i73z.onrender.com https://opticalemus.onrender.com http://localhost:4200 http://localhost:8080")))
		  
		  // http.headers(headers -> headers.frameOptions(frameOptions ->
		  // frameOptions.sameOrigin()))
		  // http.headers(headers -> headers.frameOptions(frameOptions ->
		  // frameOptions.disable()))
		  .csrf(csrf -> csrf.disable())
		  .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		  .authorizeHttpRequests(auth -> auth.requestMatchers(ENDPOINTS_WHITELIST).permitAll().anyRequest().authenticated())
		  .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
		// .httpBasic(Customizer.withDefaults())
		;
		return http.build();
	}
	
}
