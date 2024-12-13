package com.system.domain.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.domain.models.postgresql.CartItem;
import com.system.domain.models.postgresql.CartItemID;
import com.system.domain.models.postgresql.Obra;
import com.system.domain.models.postgresql.Usuario;

import java.util.List;



@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemID> {
	
	
	/**
	 * Método JPQL
	 * @param usuario
	 * @return
	 */
	List<CartItem> findByUsuario(Usuario usuario);
	
	
	/**
	 * 
	 * @param usuario
	 * @param producto
	 * @return
	 */
	CartItem findByUsuarioAndProducto(Usuario usuario, Obra producto);

	
	void deleteByUsuario(Usuario usuario);
}
