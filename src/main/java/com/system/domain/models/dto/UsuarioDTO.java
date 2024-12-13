package com.system.domain.models.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.system.domain.models.postgresql.Rol;
import com.system.domain.models.postgresql.Usuario;

import lombok.Data;

@Data
public class UsuarioDTO {
	
	private Long idUsuario;
	private String username;
	private String email;
	private String nombres;
	private String primerApellido;
	private String segundoApellido;
	private Integer dia;
	private Integer mes;
	private Integer anio;
	private Boolean estatus;
	private Boolean estatusBloqueo;
	private LocalDate dateOfBirth;
	
	private Set<String> roles;
	
	public UsuarioDTO(Usuario usuario) {
		
		this.idUsuario = usuario.getIdUsuario();
		this.username = usuario.getUsername();
		this.email = usuario.getEmail();
		this.nombres = usuario.getNombres();
		this.primerApellido = usuario.getPrimerApellido();
		this.segundoApellido = usuario.getSegundoApellido();
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
