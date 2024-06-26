package com.herokuapp.kamabye.models.dto;

import lombok.Data;

@Data
public class ProductoDTOPayPal {
	
	private String nombre;
	private String sku;
	private String descripcion;
	private String categoria;
	private String precio;
	private String impuesto;
	private String cantidad;

}
