package com.system.domain.repository.postgresql;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.system.domain.models.dto.CartItemDTO;
import com.system.domain.models.postgresql.CartItem;
import com.system.domain.models.postgresql.CartItemID;
import com.system.domain.models.postgresql.Obra;
import com.system.domain.models.postgresql.Usuario;



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
	
	
	//Retornar todos los cartitem como cartitemdto
	@Query("SELECT NEW com.system.domain.models.dto.CartItemDTO(ci.usuario.idUsuario, ci.producto.idObra, ci.cantidad) FROM CartItem ci")
	Page<CartItemDTO> jpqlfindAll(Pageable pageable);
	
	
}
