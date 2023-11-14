package com.domain.system.models.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.domain.system.models.postgresql.Partitura;

import lombok.Data;

@Data
public class ObraDTO {

	private Long idObra;

	private String nombre;

	private String compositor;

	private String arreglista;

	private String letrista;

	private String genero;

	private BigDecimal precio;
	
	private Set<PartituraDTO> partituras;

	public ObraDTO(Long idObra, String nombre, String compositor, String arreglista, String letrista, String genero,
			BigDecimal precio) {
		
		this.idObra = idObra;
		this.nombre = nombre;
		this.compositor = compositor;
		this.arreglista = arreglista;
		this.letrista = letrista;
		this.genero = genero;
		this.precio = precio;
	}

}