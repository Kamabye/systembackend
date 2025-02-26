package com.system.domain.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.domain.interfaces.IShopCartService;
import com.system.domain.interfaces.IUserService;
import com.system.domain.models.postgresql.CartItem;
import com.system.domain.models.postgresql.Usuario;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping({ "apiv1/shopcart", "apiv1/shopcart/" })
@CrossOrigin(origins = { "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/", "https://kamabyeapp.onrender.com", "https://kamabyeapp.onrender.com/", "http://localhost:4200", "http://localhost:8080", "http://localhost:4200/", "http://localhost:8080/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class ShopCartController {
	
	@Autowired
	private IShopCartService shopCartService;
	
	@Autowired
	private IUserService userService;
	
	Map<String, Object> responseBody = new HashMap<>();
	
	@PreAuthorize("hasAnyRole('Administrador', 'ROLE_Administrador')")
	@GetMapping("")
	public ResponseEntity<?> cartitems(
	  @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
	  @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize) {
		
		Page<CartItem> listaCartItems;
		
		try {
			
			listaCartItems = shopCartService.findAllPage(pageNumber, pageSize);
			
			if (!listaCartItems.isEmpty()) {
				
				return new ResponseEntity<Page<CartItem>>(listaCartItems, null, HttpStatus.OK);
			}
			
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (EmptyResultDataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@GetMapping("{idUsuario}")
	public ResponseEntity<?> shopCartUsuario(
	  @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
	  @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize,
	  @PathVariable(name = "idUsuario", required = true) String idUsuarioString) {
		
//		Map<String, Object> responseBody = new HashMap<>();
		List<CartItem> listaCartItems;
		
		try {
			
			Long idUsuario = Long.valueOf(idUsuarioString);
			
			Usuario usuario = userService.findByIdUsuario(idUsuario);
			
			listaCartItems = shopCartService.shopCartByUser(usuario);
			
			if (!listaCartItems.isEmpty()) {
				
				return new ResponseEntity<List<CartItem>>(listaCartItems, null, HttpStatus.OK);
			}
			
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (EmptyResultDataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@PostMapping("")
	public ResponseEntity<?> shopCartUsuario(
	  @RequestBody CartItem cartItem) {
		
		// Map<String, Object> responseBody = new HashMap<>();
		
		try {
			
			CartItem saveCartItem = shopCartService.agregarItem(cartItem);
			
			if (saveCartItem != null) {
				return new ResponseEntity<CartItem>(saveCartItem, null, HttpStatus.OK);
				
			}
			
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (EmptyResultDataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
}
