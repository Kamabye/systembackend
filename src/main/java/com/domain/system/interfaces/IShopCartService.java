package com.domain.system.interfaces;

import java.util.List;

import com.domain.system.models.postgresql.CartItem;
import com.domain.system.models.postgresql.Obra;
import com.domain.system.models.postgresql.Usuario;

public interface IShopCartService {
	
	List<CartItem> shopCartByUser(Usuario usuario);
	
	CartItem cartItemByUsuarioAndProducto(Usuario usuario, Obra obra);
	
	Integer countItemsUsuario(Usuario usuario);
	
	CartItem agregarItemUsuario(CartItem cartItem);
	
	void eliminarCartItem (CartItem cartItem);
	
	void vaciarShopCartUsuario(Usuario usuario);
	
	//void vaciarShopCartUsuario(Long usuarioId);
	
	

}
