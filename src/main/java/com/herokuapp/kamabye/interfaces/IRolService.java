package com.herokuapp.kamabye.interfaces;

import java.util.List;

import com.herokuapp.kamabye.models.postgresql.Rol;

public interface IRolService {
	
	List<Rol> findAll();
	
	Rol findById(Long idRol);
	
	Rol save(Rol rol);
	
	Rol deleteReturn(Long idRol);
	
	void delete(Long idRol);
	
	Rol findByRol(String rol);

}
