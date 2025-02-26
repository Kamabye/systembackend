package com.system.domain.services.imp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.domain.interfaces.IShopCartService;
import com.system.domain.models.dto.CartItemDTO;
import com.system.domain.models.postgresql.CartItem;
import com.system.domain.models.postgresql.Obra;
import com.system.domain.models.postgresql.Usuario;
import com.system.domain.repository.postgresql.CartItemRepository;

@Service
//@Transactional
@Primary
public class ShopCartImpJPA implements IShopCartService {
	
	@Autowired
	private CartItemRepository shopCartRepo;
	
	@Transactional(readOnly = true)
	@Override
	public Page<CartItem> findAllPage(Integer page, Integer size) {
		
		Page<CartItem> rowsshopcart = shopCartRepo.findAll(PageRequest.of(page, size));
		// TODO Auto-generated method stub
		return rowsshopcart;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<CartItemDTO> findAllPageDTO(Integer page, Integer size) {
		
		Page<CartItemDTO> rowsshopcart = shopCartRepo.jpqlfindAll(PageRequest.of(page, size));
		// TODO Auto-generated method stub
		return rowsshopcart;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<CartItem> shopCartByUser(Usuario usuario) {
		
		return shopCartRepo.findByUsuario(usuario);
	}
	
	@Transactional(readOnly = true)
	@Override
	public CartItem cartItemByUsuarioAndProducto(Usuario usuario, Obra obra) {
		
		return shopCartRepo.findByUsuarioAndProducto(usuario, obra);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Integer countItemsUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	@Override
	public CartItem agregarItem(CartItem cartItem) {
		// SI ya existe sumar la cantidad existente con la recibida
		
		return shopCartRepo.save(cartItem);
	}
	
	@Transactional
	@Override
	public void eliminarCartItem(CartItem cartItem) {
		
		shopCartRepo.delete(cartItem);
		
	}
	
	@Transactional
	@Override
	public void vaciarShopCartUsuario(Usuario usuario) {
		shopCartRepo.deleteByUsuario(usuario);
		
	}
	
	@Transactional
	@Override
	public void agregarProducto(Usuario usuario, Obra producto, Integer cantidad) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public BigDecimal totalShopCartUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
