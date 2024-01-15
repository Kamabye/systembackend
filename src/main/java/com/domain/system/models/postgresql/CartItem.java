package com.domain.system.models.postgresql;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CartItem")
@Data
//@EqualsAndHashCode(callSuper = true)
@IdClass(CartItemID.class)
@Builder
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Genera un cosntructor para cada parámetro finales o no nulos
public class CartItem {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProducto")
	private Obra producto;

	private Integer cantidad;

	@Transient
	public BigDecimal getSubtotal() {
		// return this.producto.getPrecio() * cantidad;
		// return this.producto.getPrecio().multiply(new
		// BigDecimal(this.cantidad)).setScale(2, RoundingMode.HALF_UP);
		return this.producto.getPrecio().setScale(2, RoundingMode.HALF_UP);
	}

	@Transient
	public BigDecimal getImpuesto() {

		// return this.producto.getIva().multiply(new
		// BigDecimal(this.cantidad)).setScale(2, RoundingMode.HALF_UP);
		return this.getSubtotal().multiply(new BigDecimal(this.producto.getIva()).divide(new BigDecimal(100)))
				.setScale(2, RoundingMode.HALF_UP);
	}

	@Transient
	public BigDecimal getTotal() {
		// return this.producto.getPrecio() * cantidad;
		// return this.producto.getPrecio().multiply(new
		// BigDecimal(this.cantidad)).setScale(2, RoundingMode.HALF_UP);
		// return this.getSubtotal().multiply(new BigDecimal());
		return this.getSubtotal().add(this.getImpuesto()).setScale(2, RoundingMode.HALF_UP);
	}

}
