package com.system.domain.model.postgresql;

import java.io.Serializable;

import lombok.Data;

@Data
public class CartItemID implements Serializable {
	
	private Long usuario;
	
	private Long producto;
	
	private static final long serialVersionUID = 1L;
	/*
	 * @Override public boolean equals(Object o) { if (this == o) return true; if (o
	 * == null || getClass() != o.getClass()) return false; CartItemID that =
	 * (CartItemID) o; return Objects.equals(usuario, that.usuario) &&
	 * Objects.equals(producto, that.producto); }
	 * 
	 * @Override public int hashCode() { return Objects.hash(usuario, producto); }
	 */
	
}