package com.domain.system.models.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
	
	private String nombre;
    private String email;

    public UsuarioDTO(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

}
