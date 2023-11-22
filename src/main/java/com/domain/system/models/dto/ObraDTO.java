package com.domain.system.models.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.domain.system.models.postgresql.Partitura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Crea un constructor que solicita todos los parámetros de la clase que no son
					// FINAL
public class ObraDTO {

	private Long id;

	private String nombre;

	private String compositor;

	private String arreglista;

	private String letrista;

	private String genero;

	private BigDecimal precio;
	
	private Set<PartituraDTO> partituras;

	public ObraDTO(Long idObra, String nombre, String compositor, String arreglista, String letrista, String genero,
			BigDecimal precio) {
		
		this.id = idObra;
		this.nombre = nombre;
		this.compositor = compositor;
		this.arreglista = arreglista;
		this.letrista = letrista;
		this.genero = genero;
		this.precio = precio;
	}

}