package com.herokuapp.kamabye.interfaces;

import java.util.List;

import com.herokuapp.kamabye.models.postgresql.CartItem;
import com.herokuapp.kamabye.models.postgresql.Obra;
import com.herokuapp.kamabye.models.postgresql.Usuario;

public interface IShopCartService {
	
	List<CartItem> shopCartByUser(Usuario usuario);
	
	CartItem cartItemByUsuarioAndProducto(Usuario usuario, Obra obra);
	
	Integer countItemsUsuario(Usuario usuario);
	
	CartItem agregarItemUsuario(CartItem cartItem);
	
	void eliminarCartItem (CartItem cartItem);
	
	void vaciarShopCartUsuario(Usuario usuario);
	
	//void vaciarShopCartUsuario(Long usuarioId);
	
	

}
