package com.herokuapp.kamabye.services.imp;

import java.sql.Blob;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herokuapp.kamabye.interfaces.IUserService;
import com.herokuapp.kamabye.interfaces.DTOProyecciones.IUsuarioDTO;
import com.herokuapp.kamabye.models.dto.UsuarioDTO;
import com.herokuapp.kamabye.models.postgresql.Rol;
import com.herokuapp.kamabye.models.postgresql.Usuario;
import com.herokuapp.kamabye.repository.postgresql.RolRepository;
import com.herokuapp.kamabye.repository.postgresql.UserRepository;

@Service
//@Transactional
@Primary
public class UserServiceImpJpa implements IUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RolRepository rolRepo;

	@Autowired
	private PasswordEncoder pswEncode;

	/**
	 * Métodos de servicio que utilizan Derived Query Methods del Repositorio
	 */

	@Transactional
	@Override
	public Usuario save(Usuario usuario) {

		usuario.setPassword(pswEncode.encode(usuario.getPassword()));

		return userRepo.save(usuario);

	}

	@Transactional
	@Override
	public UsuarioDTO guardarDTO(Usuario usuario) {

		return toDTO(save(usuario));
	}

	@Transactional
	@Override
	public List<Usuario> saveAll(List<Usuario> usuarios) {
		return userRepo.saveAll(usuarios);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findAll() {

		// Sort sort = Sort.by("nombres").ascending();
		// return userRepository.findAll(sort);
		return userRepo.findAll(Sort.by("nombres").ascending());
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> findAll(Integer page, Integer size) {

		// Ordenar los resultados por múltiples campos, cada uno con su dirección de
		// ordenación
		Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "apellidoPaterno");
		Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "apellidoMaterno");
		Sort.Order order3 = new Sort.Order(Sort.Direction.ASC, "nombres");
		Sort.Order order4 = new Sort.Order(Sort.Direction.DESC, "idUsuario");

		// Sort sort = Sort.by(Sort.Order.asc("nombres"));

		// return userRepo.findAll(PageRequest.of(page, size, sort));
		// Page<Usuario> pageUsuario = userRepo.findAll(PageRequest.of(page, size,
		// Sort.by(order1, order2, order3)));
		Page<Usuario> pageUsuario = userRepo.findAll(PageRequest.of(page, size, Sort.by(order4)));

		boolean tieneContenido = pageUsuario.hasContent();
		List<Usuario> usuarios = pageUsuario.getContent();
		long totalElementos = pageUsuario.getTotalElements();
		int totalPaginas = pageUsuario.getTotalPages();
		boolean esUltimaPagina = pageUsuario.isLast();
		int tamanoPagina = pageUsuario.getSize();
		int numeroPagina = pageUsuario.getNumber();
		int numeroElementosPagina = pageUsuario.getNumberOfElements();
		boolean esPrimeraPagina = pageUsuario.isFirst();
		boolean estaVacio = pageUsuario.isEmpty();

		boolean haySiguiente = pageUsuario.hasNext();
		boolean hayAnterior = pageUsuario.hasPrevious();
		Pageable siguientePagina = pageUsuario.nextPageable();
		Pageable paginaAnterior = pageUsuario.previousPageable();

		return pageUsuario;

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

		return userRepo.findAll(example);
	}

	@Transactional(readOnly = true)
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
		return userRepo.findAll(example, pageable);
	}

	@Transactional
	@Override
	public void delete(Long idUsuario) {
		userRepo.deleteById(idUsuario);
	}

	@Transactional
	@Override
	public Usuario deleteReturn(Long idUsuario) {
		Optional<Usuario> optional = userRepo.findById(idUsuario);
		if (!optional.isEmpty()) {
			userRepo.deleteById(idUsuario);
			return optional.get();
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Usuario findByIdUsuario(Long idUsuario) {

		/*
		 * Optional<Usuario> optional = userRepo.findById(idUsuario); if
		 * (!optional.isEmpty()) { return optional.get(); } return null;
		 */

		return userRepo.findById(idUsuario)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + idUsuario));
	}

	@Transactional(readOnly = true)
	@Override
	public Usuario findByEmail(String email) {

		return userRepo.findByEmail(email)
				.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con email: " + email));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByNombres(String nombres) {
		return userRepo.findByNombres(nombres);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByApellidoPaterno(String apellidoPaterno) {
		return userRepo.findByApellidoPaternoOrderByNombresAsc(apellidoPaterno);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByApellidoMaterno(String apellidoMaterno) {
		return userRepo.findByApellidoMaternoOrderByNombresAsc(apellidoMaterno);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findByNombresOrApellidoPaternoOrApellidoMaterno(String string) {
		return userRepo.findByNombresContainingOrApellidoPaternoContainingOrApellidoMaternoContainingOrderByNombresAsc(
				string, string, string);
	}

	@Transactional(readOnly = true)
	@Override
	public List<IUsuarioDTO> jpqlfindAllUsuariosIDTO() {

		return userRepo.jpqlfindAllUsuariosIDTO();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<IUsuarioDTO> jpqlfindAllUsuariosIDTOPageable(Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return userRepo.jpqlfindAllUsuariosIDTOPageable(pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<UsuarioDTO> jpqlfindAllUsuariosDTOPageable(Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return userRepo.jpqlfindAllUsuariosDTOPageable(pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<IUsuarioDTO> sqlfindAllUsuariosIDTOPageable(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		return userRepo.sqlfindAllUsuariosIDTOPageable(pageable);
	}

	private UsuarioDTO toDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO(usuario);

		return dto;
	}

	@Override
	public Page<Usuario> obtenerSiguientePagina(Page<Usuario> paginaActual) {
		Pageable siguientePageable = paginaActual.nextPageable();
		return userRepo.findAll(siguientePageable);
	}

	@Override
	public Page<Usuario> obtenerAnteriorPagina(Page<Usuario> paginaActual) {
		Pageable anteriorPageable = paginaActual.previousPageable();
		return userRepo.findAll(anteriorPageable);
	}

	@Override
	public Long countByIdUsuario(Long idUsuario) {
		return userRepo.countByIdUsuario(idUsuario);
	}

	@Transactional
	@Override
	public UsuarioDTO updateDTO(Usuario usuario) {
		return toDTO(this.update(usuario));
	}

	@Transactional
	@Override
	public Usuario update(Usuario usuario) {

		Usuario usuarioPrevio = this.findByIdUsuario(usuario.getIdUsuario());

		if (usuario.getUsername() != null && !usuario.getUsername().equals(usuarioPrevio.getUsername())) {
			usuarioPrevio.setUsername(usuario.getUsername());
		}
		if (usuario.getPassword() != null && !usuario.getPassword().equals(usuarioPrevio.getPassword())) {
			usuarioPrevio.setPassword(pswEncode.encode(usuario.getPassword()));
			// usuarioPrevio.setPassword(usuario.getPassword());
		}
		if (usuario.getEmail() != null && !usuario.getEmail().equals(usuarioPrevio.getEmail())) {
			usuarioPrevio.setEmail(usuario.getEmail());
		}
		if (usuario.getNombres() != null && !usuario.getNombres().equals(usuarioPrevio.getNombres())) {
			usuarioPrevio.setNombres(usuario.getNombres());
		}
		if (usuario.getApellidoPaterno() != null
				&& !usuario.getApellidoPaterno().equals(usuarioPrevio.getApellidoPaterno())) {
			usuarioPrevio.setApellidoPaterno(usuario.getApellidoPaterno());
		}
		if (usuario.getApellidoMaterno() != null
				&& !usuario.getApellidoMaterno().equals(usuarioPrevio.getApellidoMaterno())) {
			usuarioPrevio.setApellidoMaterno(usuario.getApellidoMaterno());
		}
		if (usuario.getDia() != null && !usuario.getDia().equals(usuarioPrevio.getDia())) {
			usuarioPrevio.setDia(usuario.getDia());
		}
		if (usuario.getMes() != null && !usuario.getMes().equals(usuarioPrevio.getMes())) {
			usuarioPrevio.setMes(usuario.getMes());
		}
		if (usuario.getAnio() != null && !usuario.getAnio().equals(usuarioPrevio.getAnio())) {
			usuarioPrevio.setAnio(usuario.getAnio());
		}
		if (usuario.getEstatus() != null && !usuario.getEstatus().equals(usuarioPrevio.getEstatus())) {
			usuarioPrevio.setEstatus(usuario.getEstatus());
		}
		if (usuario.getEstatusBloqueo() != null
				&& !usuario.getEstatusBloqueo().equals(usuarioPrevio.getEstatusBloqueo())) {
			usuarioPrevio.setEstatusBloqueo(usuario.getEstatusBloqueo());
		}
		if (usuario.getDateOfBirth() != null && !usuario.getDateOfBirth().equals(usuarioPrevio.getDateOfBirth())) {
			usuarioPrevio.setDateOfBirth(usuario.getDateOfBirth());
		}

		if (usuario.getImagen() != null) {

			usuarioPrevio.setImagen(usuario.getImagen());
		}

		if (!usuario.getRoles().isEmpty()) {
			// usuarioPrevio.getRoles().clear();

			Set<Rol> roles = new HashSet<>();
			for (Rol rol : usuario.getRoles()) {
				Optional<Rol> existingRol = rolRepo.findById(rol.getIdRol());
				existingRol.ifPresent(roles::add);
			}
			usuarioPrevio.setRoles(roles);

			// usuarioPrevio.setRoles(usuario.getRoles());
		}

		return userRepo.save(usuarioPrevio);
	}

	@Override
	public Usuario uploadImage(Long IdUsuario, Blob image) {
		Usuario usuarioTemp = userRepo.findById(IdUsuario).get();

		usuarioTemp.setImagen(image);
		return userRepo.save(usuarioTemp);
	}

}
