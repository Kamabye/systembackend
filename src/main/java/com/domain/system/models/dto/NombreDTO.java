package com.domain.system.models.dto;

import lombok.Data;

@Data
public class NombreDTO {

	private String nombre;

	public NombreDTO(String nombre) {
        this.nombre = nombre;
    }

}
