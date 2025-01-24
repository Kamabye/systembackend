package com.system.domain.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Crea un constructor que solicita todos los parámetros de la clase que no son
// FINAL
public class ObraDTO {
	
	private Long idObra;
	
	private String nombre;
	
	private String compositor;
	
	private String arreglista;
	
	private String letrista;
	
	private String genero;
	
	private BigDecimal precio;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime modifiedAt;
	
	private String embedAudio;
	
	private String embedVideo;
	
	private Set<PartituraDTO> partituras;
	
	public ObraDTO(Long idObra, String nombre, String compositor, String arreglista, String letrista, String genero,
	  BigDecimal precio, String embedAudio, String embedVideo, LocalDateTime createdAt, LocalDateTime modifiedAt) {
		
		this.idObra = idObra;
		this.nombre = nombre;
		this.compositor = compositor;
		this.arreglista = arreglista;
		this.letrista = letrista;
		this.genero = genero;
		this.precio = precio;
		this.embedAudio = embedAudio;
		this.embedVideo = embedVideo;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		
	}
	
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