package com.system.domain.model.postgresql;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "CartItem", indexes = {
  @Index(name = "idx_cart_item_id_usuario_id_producto", columnList = "idUsuario,idProducto")
})
@Data
@IdClass(CartItemID.class)
public class CartItem {
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProducto")
	private Obra producto;
	
	private Long cantidad;
	
	@Transient
	public BigDecimal getSubtotal() {
		return this.producto.getPrecio().multiply(BigDecimal.valueOf(this.cantidad)).setScale(2, RoundingMode.HALF_UP);
	}
	
	@Transient
	public BigDecimal getImpuesto() {
		return this.producto.getIva().multiply(this.getSubtotal()).setScale(2, RoundingMode.HALF_UP);
	}
	
	@Transient
	public BigDecimal getTotal() {
		return this.getSubtotal().add(this.getImpuesto()).setScale(2, RoundingMode.HALF_UP);
	}
	
}
