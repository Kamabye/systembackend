package com.system.domain.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	//@Value("${jwt.secretkey}")
	//private String secretKey;

	private static String SECRET_KEY;
	private static Long EXPIRATION_TIME;

	//@Value("${jwt.expiration}")
	//private Long expiration;

	// private static final String SECRET_KEY =
	// "462D4A614E645267556B586E3272357538782F413F4428472B4B625065536856";
	
	@Value("${jwt.secretkey}")
	public void setSecretKeyStatic(String secretKey) {
		JwtService.SECRET_KEY = secretKey;
	}
	
	@Value("${jwt.expiration}")
	public void setSecretKeyStatic(Long expiration) {
		JwtService.EXPIRATION_TIME = expiration;
	}

	public String generateToken(UserDetails userDetails) {
		return createToken(new HashMap<>(), userDetails);
	}

	public String generateToken(String username) {
		return createToken(new HashMap<>(), username);
	}

	private String createToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.claim("authorities",
						userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
								.collect(Collectors.joining(",")))
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.signWith(getSingInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private String createToken(Map<String, Object> extraClaims, String username) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
		return Jwts.builder().setClaims(extraClaims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(expirationDate)
				.signWith(getSingInKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public String extractAuthorities(String token) {
		final Claims claims = extractAllClaims(token);
		return (String) claims.get("authorities");
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Key getSingInKey() {
		byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBites);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSingInKey()).build().parseClaimsJws(token).getBody();
	}
}
