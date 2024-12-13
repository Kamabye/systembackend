package com.system.domain.services.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.domain.interfaces.IRolService;
import com.system.domain.models.postgresql.Rol;
import com.system.domain.repository.postgresql.RolRepository;

@Service
//@Transactional
@Primary
public class RolServiceImpJpa implements IRolService {

	@Autowired
	private RolRepository rolRepo;

	@Transactional(readOnly = true)
	@Override
	public List<Rol> findAll() {

		return rolRepo.findAll(Sort.by("rol").descending());
	}

	@Transactional(readOnly = true)
	@Override
	public Rol findById(Long idRol) {
		Optional<Rol> optional = rolRepo.findById(idRol);
		if (!optional.isEmpty()) {
			return optional.get();
		}
		return null;
	}

	@Transactional
	@Override
	public Rol save(Rol rol) {

		return rolRepo.save(rol);

	}

	@Transactional
	@Override
	public Rol deleteReturn(Long idRol) {
		Optional<Rol> optional = rolRepo.findById(idRol);
		if (!optional.isEmpty()) {
			rolRepo.deleteById(idRol);
			return optional.get();
		}
		return null;
	}

	@Transactional
	@Override
	public void delete(Long idRol) {

		rolRepo.deleteById(idRol);

	}

	@Transactional(readOnly = true)
	@Override
	public Rol findByRol(String rol) {
		Optional<Rol> optional = rolRepo.findByRol(rol);
		if (!optional.isEmpty()) {
			return optional.get();
		}
		return null;
	}

}
