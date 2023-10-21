package com.domain.system.services.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.system.interfaces.IUserService;
import com.domain.system.models.postgresql.Usuario;
import com.domain.system.repository.postgresql.UserRepository;

@Service
//@Transactional
@Primary
public class UserServiceImpJpa implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public Usuario save(Usuario usuario) {
		return userRepository.save(usuario);
	}

	@Transactional
	@Override
	public List<Usuario> saveAll(List<Usuario> usuarios) {
		return userRepository.saveAll(usuarios);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findAll() {

		// Sort sort = Sort.by("nombres").ascending();
		// return userRepository.findAll(sort);
		return userRepository.findAll(Sort.by("nombres").ascending());
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> findAllPage(Integer page, Integer size) {

		Sort sort = Sort.by(Sort.Order.asc("nombres"));

		PageRequest pageable = PageRequest.of(page, size, sort);

		return userRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByExample(Usuario usuario) {
		ExampleMatcher matcher = ExampleMatcher.matching()// Cuando se requiere OR
				// ExampleMatcher matcher = ExampleMatcher.matchingAll() //Cuando se requiere
				// AND
				// ExampleMatcher matcher = ExampleMatcher.matchingAny() //Cuando se requiere
				// almenos un OR
				.withIgnorePaths("id") // Ignorar el campo "id" durante la
										// búsqueda
				// realiza una búsqueda de texto que contiene la cadena proporcionada, ignorando
				// las mayúsculas y minúsculas.
				.withMatcher("nombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				// realiza una búsqueda que ignora las mayúsculas y minúsculas para todos los
				// campos.
				.withIgnoreCase()
				// realiza una búsqueda de texto que comienza con la cadena proporcionada.
				.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith())
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

		Example<Usuario> example = Example.of(usuario, matcher);
		// se utiliza para realizar una consulta con condiciones OR en lugar de AND.
		// Esto significa que devolverá registros que coincidan con cualquiera de los
		// atributos proporcionados en el ejemplo.
		// Example<Usuario> example = Example.of(ejemploUsuario, matcher,
		// ExampleMatcher.matchingAny());

		return userRepository.findAll(example);
	}

	@Override
	public Page<Usuario> findByExampleWithPage(Usuario usuario, Integer page, Integer size) {

		ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id") // Ignorar el campo "id" durante la
		// búsqueda
// realiza una búsqueda de texto que contiene la cadena proporcionada, ignorando
// las mayúsculas y minúsculas.
				.withMatcher("nombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
// realiza una búsqueda que ignora las mayúsculas y minúsculas para todos los
// campos.
				.withIgnoreCase()
// realiza una búsqueda de texto que comienza con la cadena proporcionada.
				.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith())
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

		Example<Usuario> example = Example.of(usuario, matcher);
// se utiliza para realizar una consulta con condiciones OR en lugar de AND.
// Esto significa que devolverá registros que coincidan con cualquiera de los
// atributos proporcionados en el ejemplo.
// Example<Usuario> example = Example.of(ejemploUsuario, matcher,
// ExampleMatcher.matchingAny());
		Sort sort = Sort.by(Sort.Order.asc("nombres"));
		PageRequest pageable = PageRequest.of(page, size, sort);
		return userRepository.findAll(example, pageable);
	}

	@Transactional
	@Override
	public void delete(Integer idUsuario) {
		userRepository.deleteById(idUsuario);
	}

	@Transactional(readOnly = true)
	@Override
	public Usuario findById(Long idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Usuario findByEmail(String email) {
		Optional<Usuario> optional = userRepository.findByEmail(email);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByNombres(String nombres) {
		return userRepository.findByNombres(nombres);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByApellidoPaterno(String apellidoPaterno) {
		return userRepository.findByApellidoPaterno(apellidoPaterno);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByApellidoMaterno(String apellidoMaterno) {
		return userRepository.findByApellidoMaterno(apellidoMaterno);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByNombresOrApellidoPaternoOrApellidoMaterno(String string) {
		return userRepository
				.findByNombresContainingOrApellidoPaternoContainingOrApellidoMaternoContainingOrderByNombresAsc(string,
						string, string);
	}

}
