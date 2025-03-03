package com.system.domain.service.interfaces;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.system.domain.model.dto.CartItemDTO;
import com.system.domain.model.postgresql.CartItem;
import com.system.domain.model.postgresql.Obra;
import com.system.domain.model.postgresql.Usuario;

public interface IShopCartService {
	
	Page<CartItem> findAllPage(Integer page, Integer size);
	
	Page<CartItemDTO> findAllPageDTO(Integer page, Integer size);
	
	Page<CartItem> shopCartByUser(Usuario usuario, Integer page, Integer size);
	
	CartItem findByID(CartItem cartItem);
	
	CartItem cartItemByUsuarioAndProducto(Usuario usuario, Obra obra);
	
	Integer countItemsUsuario(Usuario usuario);
	
	CartItem agregarItem(CartItem cartItem);
	
	void agregarProducto(Usuario usuario, Obra producto, Integer cantidad);
	
	void eliminarCartItem(CartItem cartItem);
	
	void vaciarShopCartUsuario(Usuario usuario);
	
	BigDecimal totalShopCartUsuario(Usuario usuario);
	
	Long shopCartCountByUser(Long idUsuario);
	
}
