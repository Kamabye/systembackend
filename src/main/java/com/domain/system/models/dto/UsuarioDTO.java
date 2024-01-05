package com.domain.system.models.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UsuarioDTO {

	private Integer id;
	private String username;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String email;
	private Boolean estatus;
	private Boolean estatusBloqueo;
	private Date createdAt;
	private Date modifiedAt;

	public UsuarioDTO(Integer id, String username, String email, String nombres, String apellidoPaterno,
			String apellidoMaterno, Boolean estatus, Boolean estatusBloqueo) {
		this.username = username;
		this.email = email;
	}

}
