package com.system.domain.service.implementation;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.domain.model.dto.CartItemDTO;
import com.system.domain.model.postgresql.CartItem;
import com.system.domain.model.postgresql.CartItemID;
import com.system.domain.model.postgresql.Obra;
import com.system.domain.model.postgresql.Usuario;
import com.system.domain.repository.postgresql.CartItemRepository;
import com.system.domain.service.interfaces.IShopCartService;

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
	public Page<CartItem> shopCartByUser(Usuario usuario, Integer pageNumber, Integer pageSize) {
		
		return shopCartRepo.findByUsuario(usuario, PageRequest.of(pageNumber, pageSize));
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
	
	@Override
	public CartItem findByID(CartItem cartItem) {
		
		CartItemID cartItemID = new CartItemID();
		cartItemID.setProducto(cartItem.getProducto().getIdObra());
		cartItemID.setUsuario(cartItem.getUsuario().getIdUsuario());
		
		Optional<CartItem> cartItemSave = shopCartRepo.findById(cartItemID);
		
		if (cartItemSave.isPresent()) {
			return cartItemSave.get();
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Long shopCartCountByUser(Long idUsuario) {
		return shopCartRepo.countCartItemByUser(idUsuario);
	}
	
}
