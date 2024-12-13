package com.system.domain.interfaces;

import java.util.List;

import com.system.domain.models.postgresql.Rol;

public interface IRolService {
	
	List<Rol> findAll();
	
	Rol findById(Long idRol);
	
	Rol save(Rol rol);
	
	Rol deleteReturn(Long idRol);
	
	void delete(Long idRol);
	
	Rol findByRol(String rol);

}
