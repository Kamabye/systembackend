package com.system.domain.repository.postgresql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.system.domain.model.dto.CartItemDTO;
import com.system.domain.model.postgresql.CartItem;
import com.system.domain.model.postgresql.CartItemID;
import com.system.domain.model.postgresql.Obra;
import com.system.domain.model.postgresql.Usuario;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemID> {
	
	/**
	 * Método JPQL
	 * 
	 * @param usuario
	 * @return
	 */
	Page<CartItem> findByUsuario(Usuario usuario, Pageable pageable);
	
	/**
	 * 
	 * @param usuario
	 * @param producto
	 * @return
	 */
	CartItem findByUsuarioAndProducto(Usuario usuario, Obra producto);
	
	void deleteByUsuario(Usuario usuario);
	
	// Retornar todos los cartitem como cartitemdto
	@Query("SELECT NEW com.system.domain.model.dto.CartItemDTO(ci.usuario.idUsuario, ci.producto.idObra, ci.cantidad) FROM CartItem ci")
	Page<CartItemDTO> jpqlfindAll(Pageable pageable);
	
	@Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.usuario.idUsuario = :idUsuario")
	Long countCartItemByUser(Long idUsuario);
}
