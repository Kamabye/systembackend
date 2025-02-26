package com.system.domain.services;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.domain.models.postgresql.Token;
import com.system.domain.repository.postgresql.TokenRepository;
import com.system.domain.userdetails.UserDetailsImp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Autowired
	private TokenRepository tokenRepository;
	// @Value("${jwt.secretkey}")
	// private String secretKey;
	
	private final String SECRET_KEY;
	// private final Long EXPIRATION_TIME;
	
	// @Value("${jwt.expiration}")
	// private Long expiration;
	
	// private static final String SECRET_KEY =
	// "462D4A614E645267556B586E3272357538782F413F4428472B4B625065536856";
	
	/*
	 * @Value("${jwt.secretkey}") public void setSecretKeyStatic(String secretKey) {
	 * JwtService.SECRET_KEY = secretKey; }
	 * 
	 * @Value("${jwt.expiration}") public void setSecretKeyStatic(Long expiration) {
	 * JwtService.EXPIRATION_TIME = expiration; }
	 */
	
	public JwtService(
	  @Value("${jwt.secretkey}") String secretKey,
	  @Value("${jwt.expirationtime}") Long expirationTime) {
		this.SECRET_KEY = secretKey;
		// this.EXPIRATION_TIME = expirationTime;
	}
	
	@Transactional
	public String generateToken(UserDetailsImp userDetails) throws ExpiredJwtException {
		// return createToken(new HashMap<>(), userDetails.getUsername(),
		// userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
		return createToken(new HashMap<>(), userDetails, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
	}
	
	private String createToken(Map<String, Object> extraClaims, UserDetailsImp userDetails, String[] authorities) {
		
		// String existingToken = getExistingToken(username);
		String existingToken = getExistingToken(userDetails.getUsuario().getIdUsuario());
		
		if (existingToken != null && isTokenValid(existingToken)) {
			// System.out.println("Hay token almacenados válidos");
			return existingToken;
		}
		
		// System.out.println("No hay token almacenados válidos");
		// DateTimeFormatter formatter =
		// DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
		LocalDateTime nowLocalDateTime = LocalDateTime.now();
		// String formatted = nowLocalDateTime.format(formatter);
		// LocalDateTime nowLocalDateTimeFormatted = LocalDateTime.parse(formatted,
		// formatter);
		// LocalDateTime expLocalDateTime = nowLocalDateTime.plusDays(1);
		// LocalDateTime expLocalDateTime = nowLocalDateTime.plusMinutes(1);
		LocalDateTime expLocalDateTime = nowLocalDateTime.plusWeeks(1);
		Date now = Date.from(nowLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
		// Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
		Date expirationDate = Date.from(expLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
//Guardar el token en la base de datos
		Token tokenEntity = new Token();
		
		if (authorities != null && authorities.length > 0) {
			String token = Jwts.builder()
			  .setClaims(extraClaims)
			  .setSubject(userDetails.getUsername())
			  // .claim("authorities",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
			  .claim("authorities", authorities)
			  .setIssuedAt(now)
			  .setExpiration(expirationDate)
			  .signWith(getSingInKey(), SignatureAlgorithm.HS256)
			  .compact();
			
			// tokenEntity.setUsername(username);
			// System.out.println(userDetails.getUsuario().getIdUsuario());
			tokenEntity.setIdUsuario(userDetails.getUsuario().getIdUsuario());
			tokenEntity.setToken(token);
			tokenEntity.setExpirationDate(expLocalDateTime);
			tokenRepository.save(tokenEntity);
			
			return token;
		}
		String token = Jwts.builder()
		  .setClaims(extraClaims)
		  .setSubject(userDetails.getUsername())
		  .setIssuedAt(now)
		  .setExpiration(expirationDate)
		  .signWith(getSingInKey(), SignatureAlgorithm.HS256)
		  .compact();
		
		// System.out.println(userDetails.getUsuario().getIdUsuario());
		// tokenEntity.setUsername(userDetails.getUsername());
		tokenEntity.setIdUsuario(userDetails.getUsuario().getIdUsuario());
		tokenEntity.setToken(token);
		tokenEntity.setExpirationDate(expLocalDateTime);
		tokenRepository.save(tokenEntity);
		
		return token;
		
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
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
		  .setSigningKey(getSingInKey())
		  .build()
		  .parseClaimsJws(token)
		  .getBody();
	}
	
	private Key getSingInKey() {
		byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBites);
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean isTokenValid(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(getSingInKey()).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	private String getExistingToken(Long idUsuario) {
		// Optional<String> optional =
		// tokenRepository.findLastTokenByUsername(username);
		Optional<String> optional = tokenRepository.findLastTokenByidUsuario(idUsuario);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		return null; // Cambia esto según tu implementación
	}
	
}
