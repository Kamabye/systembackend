package com.domain.system.models.dto;

import lombok.Data;

@Data
public class PartituraDTO {

	private Long idPartitura;

	private String instrumento;

	private Long idObra;

	public PartituraDTO(Long idPartitura, String instrumento, Long idObra) {
		this.idPartitura = idPartitura;
		this.instrumento = instrumento;
		this.idObra = idObra;

	}

}
