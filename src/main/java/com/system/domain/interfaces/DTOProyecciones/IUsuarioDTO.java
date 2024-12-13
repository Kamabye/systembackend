package com.system.domain.interfaces.DTOProyecciones;

import java.util.Date;
import java.util.Set;

public interface IUsuarioDTO {

	Long getId();

	String getUsername();

	String getEmail();

	String getNombres();

	String getApellidoPaterno();

	String getApellidoMaterno();
	
	Integer getDia();
	
	Integer getMes();
	
	Integer getAnio();
	
	String getEstatus();
	
	String getEstatusBloqueo();
	
	Date getDateOfBirth();
	
	Date getCreatedAt();
	
	Date getModifiedAt();
	
	Set<String> getRoles();

}
