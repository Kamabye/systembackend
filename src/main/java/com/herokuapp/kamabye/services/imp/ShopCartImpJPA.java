package com.herokuapp.kamabye.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herokuapp.kamabye.interfaces.IShopCartService;
import com.herokuapp.kamabye.models.postgresql.CartItem;
import com.herokuapp.kamabye.models.postgresql.Obra;
import com.herokuapp.kamabye.models.postgresql.Usuario;
import com.herokuapp.kamabye.repository.postgresql.CartItemRepository;

@Service
//@Transactional
@Primary
public class ShopCartImpJPA implements IShopCartService{
	
	@Autowired
	private CartItemRepository shopCartRepo;

	@Transactional(readOnly = true)
	@Override
	public List<CartItem> shopCartByUser(Usuario usuario) {

		return shopCartRepo.findByUsuario(usuario);
	}

	@Override
	public CartItem cartItemByUsuarioAndProducto(Usuario usuario, Obra obra) {

		return shopCartRepo.findByUsuarioAndProducto(usuario, obra);
	}

	@Override
	public Integer countItemsUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartItem agregarItemUsuario(CartItem cartItem) {
		return shopCartRepo.save(cartItem);
	}

	@Override
	public void eliminarCartItem(CartItem cartItem) {
		
		shopCartRepo.delete(cartItem);
		
	}

	@Override
	public void vaciarShopCartUsuario(Usuario usuario) {
		shopCartRepo.deleteByUsuario(usuario);
		
	}

}
