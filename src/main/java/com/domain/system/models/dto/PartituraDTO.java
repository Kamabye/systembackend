package com.domain.system.models.dto;

import lombok.Data;

@Data
public class PartituraDTO {

	private Long idPartitura;

	private String instrumento;

	public PartituraDTO(Long idPartitura, String instrumento) {
		this.idPartitura = idPartitura;
		this.instrumento = instrumento;

	}

}
