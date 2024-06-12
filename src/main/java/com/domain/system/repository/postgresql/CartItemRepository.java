package com.domain.system.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.system.models.postgresql.CartItem;
import com.domain.system.models.postgresql.CartItemID;
import java.util.List;
import com.domain.system.models.postgresql.Usuario;
import com.domain.system.models.postgresql.Obra;



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
