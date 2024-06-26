package com.herokuapp.kamabye.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.herokuapp.kamabye.interfaces.IShopCartService;
import com.herokuapp.kamabye.models.postgresql.CartItem;
import com.herokuapp.kamabye.models.postgresql.Usuario;

@RestController
@RequestMapping({ "apiv1/shopcart", "apiv1/shopcart/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200" }, methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})

public class ShopCartController {
	
	@Autowired
	private IShopCartService shopCartService;
	
	@GetMapping("")
	public ResponseEntity<?> cartitems() {
		
		Map<String, Object> responseBody = new HashMap<>();
		List<CartItem> listaCartItems;
		
		try {
			Usuario usuario = new Usuario();
			usuario.setIdUsuario((long) 1);
			
			listaCartItems = shopCartService.shopCartByUser(usuario);
			
			if (!listaCartItems.isEmpty()) {

				return new ResponseEntity<List<CartItem>>(listaCartItems, null, HttpStatus.OK);
			}
			
			responseBody.put("mensaje", "No se encontraron resultados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

		}
		catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "No se encontraron resultados.");
			responseBody.put("error", "EmptyResultDataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
		
	}

}
