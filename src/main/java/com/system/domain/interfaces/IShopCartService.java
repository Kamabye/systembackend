package com.system.domain.interfaces;

import java.util.List;

import com.system.domain.models.postgresql.CartItem;
import com.system.domain.models.postgresql.Obra;
import com.system.domain.models.postgresql.Usuario;

public interface IShopCartService {
	
	List<CartItem> shopCartByUser(Usuario usuario);
	
	CartItem cartItemByUsuarioAndProducto(Usuario usuario, Obra obra);
	
	Integer countItemsUsuario(Usuario usuario);
	
	CartItem agregarItemUsuario(CartItem cartItem);
	
	void eliminarCartItem (CartItem cartItem);
	
	void vaciarShopCartUsuario(Usuario usuario);
	
	//void vaciarShopCartUsuario(Long usuarioId);
	
	

}
