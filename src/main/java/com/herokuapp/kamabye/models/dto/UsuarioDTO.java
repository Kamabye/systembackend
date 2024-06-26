package com.herokuapp.kamabye.models.dto;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.herokuapp.kamabye.models.postgresql.Rol;
import com.herokuapp.kamabye.models.postgresql.Usuario;

import lombok.Data;

@Data
public class UsuarioDTO {

	private Long idUsuario;
	private String username;
	private String email;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Integer dia;
	private Integer mes;
	private Integer anio;
	private Boolean estatus;
	private Boolean estatusBloqueo;
	private Date dateOfBirth;

	private Set<String> roles;

	public UsuarioDTO(Usuario usuario) {
		
		this.idUsuario = usuario.getIdUsuario();
		this.username = usuario.getUsername();
		this.email = usuario.getEmail();
		this.nombres = usuario.getNombres();
		this.apellidoPaterno = usuario.getApellidoPaterno();
		this.apellidoMaterno = usuario.getApellidoMaterno();
		this.dia = usuario.getDia();
		this.mes = usuario.getMes();
		this.anio = usuario.getAnio();
		this.estatus = usuario.getEstatus();
		this.estatusBloqueo = usuario.getEstatusBloqueo();
		this.dateOfBirth = usuario.getDateOfBirth();
		this.roles = usuario.getRoles().stream().map(Rol::getRol).collect(Collectors.toSet());
	}
	
	public UsuarioDTO(Integer idUsuario, String username, String email, String nombres, String apellidoPaterno,
			String apellidoMaterno, Boolean estatus, Boolean estatusBloqueo) {
		this.username = username;
		this.email = email;
	}

}
