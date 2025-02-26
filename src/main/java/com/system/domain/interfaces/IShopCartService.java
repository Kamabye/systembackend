package com.system.domain.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.system.domain.models.dto.CartItemDTO;
import com.system.domain.models.postgresql.CartItem;
import com.system.domain.models.postgresql.Obra;
import com.system.domain.models.postgresql.Usuario;

public interface IShopCartService {
	
	Page<CartItem> findAllPage(Integer page, Integer size);
	
	Page<CartItemDTO> findAllPageDTO(Integer page, Integer size);
	
	List<CartItem> shopCartByUser(Usuario usuario);
	
	CartItem cartItemByUsuarioAndProducto(Usuario usuario, Obra obra);
	
	Integer countItemsUsuario(Usuario usuario);
	
	CartItem agregarItem(CartItem cartItem);
	
	void agregarProducto(Usuario usuario, Obra producto, Integer cantidad);
	
	void eliminarCartItem (CartItem cartItem);
	
	void vaciarShopCartUsuario(Usuario usuario);
	
	BigDecimal totalShopCartUsuario(Usuario usuario);

	
	
}
