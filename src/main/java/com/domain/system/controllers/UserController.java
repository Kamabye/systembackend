package com.domain.system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.domain.system.interfaces.IUserService;
import com.domain.system.models.postgresql.Rol;
import com.domain.system.models.postgresql.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping({ "apiv1/user", "apiv1/user/" })
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:4200" }, methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class UserController {

	@Autowired
	private IUserService userService;

	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	@GetMapping("")
	public ResponseEntity<?> users() {
		Map<String, Object> responseBody = new HashMap<>();
		List<Usuario> listaUsuarios;

		try {

			listaUsuarios = userService.findAll();
			

			if (!listaUsuarios.isEmpty()) {

				return new ResponseEntity<List<Usuario>>(listaUsuarios, null, HttpStatus.OK);
				
			}

			responseBody.put("mensaje", "No se encontraron resultados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "No se encontraron resultados.");
			responseBody.put("error", "EmptyResultDataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	@GetMapping("/pageable")
	public ResponseEntity<?> usersPageable(
			@RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "2") int pageSize) {
		Map<String, Object> responseBody = new HashMap<>();
		// List<Usuario> listaUsuarios;
		Page<Usuario> listaUsuarios;
		//Page<IUsuarioDTO> listaUsuariosIDTO;

		try {

			listaUsuarios = userService.findAllPage(pageNumber, pageSize);
			//listaUsuariosIDTO = userService.sqlfindAllUsuariosIDTOPageable(pageNumber, pageSize);
			//listaUsuariosIDTO = userService.jpqlfindAllUsuariosIDTOPageable(pageNumber, pageSize);

			if (!listaUsuarios.isEmpty()) {

				// return new ResponseEntity<List<Usuario>>(listaUsuarios, null, HttpStatus.OK);
				return new ResponseEntity<Page<Usuario>>(listaUsuarios, null, HttpStatus.OK);
				//return new ResponseEntity<List<Usuario>>(listaUsuarios.getContent(), null, HttpStatus.OK);
				//return new ResponseEntity<Page<IUsuarioDTO>>(listaUsuariosIDTO, null, HttpStatus.OK);
				//return new ResponseEntity<Page<IUsuarioDTO>>(listaUsuariosIDTO, null, HttpStatus.OK);
			}

			responseBody.put("mensaje", "No se encontraron resultados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "No se encontraron resultados.");
			responseBody.put("error", "EmptyResultDataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@GetMapping("{idUsuario}")
	public ResponseEntity<?> findUsuario(@PathVariable(name = "idUsuario", required = false) String idUsuarioString) {
		Map<String, Object> responseBody = new HashMap<>();

		try {

			if (idUsuarioString != null) {

				Long idUsuario = Long.valueOf(idUsuarioString);

				Usuario usuario = userService.findById(idUsuario);

				if (usuario != null) {
					System.out.println(usuario.toString());
					//usuario.setPassword("");
					//usuario.setImagen(null);

					//HttpHeaders headers = new HttpHeaders();
					//headers.setContentType(MediaType.APPLICATION_JSON);

					//return new ResponseEntity<>(usuario, headers, HttpStatus.OK);
					//return new ResponseEntity<Usuario>(usuario, null, HttpStatus.OK);
					return new ResponseEntity<Set<Rol>>(usuario.getRoles(), null, HttpStatus.OK);
					// return new ResponseEntity<Usuario>(usuario, headers, HttpStatus.OK);
				}

				responseBody.put("mensaje",
						"El ID: ".concat(idUsuarioString.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}
			responseBody.put("mensaje", "Parámetros nulos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@PostMapping("")
	public ResponseEntity<?> saveUser(@RequestPart(name = "imagen", required = false) MultipartFile imagen,
			@RequestParam(name = "usuarioJSON", required = true) String usuarioJSON) {

		Map<String, Object> responseBody = new HashMap<>();

		try {

			if (usuarioJSON != null) {
				//System.out.println(usuarioJSON);

				ObjectMapper objectMapper = new ObjectMapper();

				Usuario usuario = objectMapper.readValue(usuarioJSON, Usuario.class);
				//System.out.println(usuario);

				usuario.setPassword(usuario.getPassword());

				//System.out.println("Se va a guardar un usuario: " + usuario.toString());

				if (imagen != null) {
					usuario.setImagen(imagen.getBytes());
				}

				Usuario userSave = userService.guardar(usuario);

				if (userSave != null) {

					return new ResponseEntity<Usuario>(userSave, null, HttpStatus.OK);

				}
				responseBody.put("mensaje",
						"El Objeto :".concat(usuario.toString().concat(" no se pudo guardar en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}

			responseBody.put("mensaje", "Parámetros inválidos nulos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);

		} catch (MultipartException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Error el multipartFile");
			responseBody.put("error", "MultipartException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataIntegrityViolationException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El Objeto: ".concat(usuarioJSON.concat(" ya existe en la base de datos")));
			responseBody.put("error", "DataIntegrityViolationException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@PutMapping("{idUsuario}")
	public ResponseEntity<?> updateUser(@PathVariable(name = "idUsuario", required = true) String idUsuarioString,
			@RequestBody Usuario usuario) {
		Map<String, Object> responseBody = new HashMap<>();

		try {
			if (idUsuarioString != null && usuario != null) {

				Long idUsuario = Long.valueOf(idUsuarioString);

				Usuario userTemp = userService.findById(idUsuario);

				if (userTemp != null) {

					if (usuario.getUsername() != "") {
						userTemp.setUsername(usuario.getUsername());
					}
					if (usuario.getPassword() != "") {
						userTemp.setPassword(usuario.getPassword());
					}
					if (usuario.getEmail() != "") {
						userTemp.setEmail(usuario.getEmail());
					}
					if (usuario.getNombres() != "") {
						userTemp.setNombres(usuario.getNombres());
					}
					if (usuario.getApellidoPaterno() != "") {
						userTemp.setApellidoPaterno(usuario.getApellidoPaterno());
					}
					if (usuario.getApellidoMaterno() != "") {
						userTemp.setApellidoMaterno(usuario.getApellidoMaterno());
					}
					if (usuario.getApellidoMaterno() != "") {
						userTemp.setApellidoMaterno(usuario.getApellidoMaterno());
					}
					if (!usuario.getRoles().isEmpty()) {
						userTemp.setRoles(usuario.getRoles());
					}

					Usuario userUpdate = userService.guardar(userTemp);

					if (userUpdate != null) {

						return new ResponseEntity<Usuario>(userUpdate, null, HttpStatus.OK);

					}
					responseBody.put("mensaje", "El ID: "
							.concat(usuario.toString().concat(" no se pudo actualizar en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null,
							HttpStatus.INTERNAL_SERVER_ERROR);

				}

				responseBody.put("mensaje",
						"El ID: ".concat(usuario.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}
			responseBody.put("mensaje", "Datos inválidos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@PutMapping("")
	public ResponseEntity<?> updateUserByParam(@RequestPart(name = "imagen", required = false) MultipartFile imagen,
			@RequestParam(name = "usuarioJSON", required = true) String usuarioJSON) {
		Map<String, Object> responseBody = new HashMap<>();

		try {

			if (usuarioJSON != null) {

				ObjectMapper objectMapper = new ObjectMapper();

				Usuario usuario = objectMapper.readValue(usuarioJSON, Usuario.class);

				Usuario userTemp = userService.findById(usuario.getId());

				if (userTemp != null) {

					if (usuario.getUsername() != "") {
						userTemp.setUsername(usuario.getUsername());
					}
					if (usuario.getPassword() != "") {
						userTemp.setPassword(usuario.getPassword());
					}
					if (usuario.getEmail() != "") {
						userTemp.setEmail(usuario.getEmail());
					}
					if (usuario.getNombres() != "") {
						userTemp.setNombres(usuario.getNombres());
					}
					if (usuario.getApellidoPaterno() != "") {
						userTemp.setApellidoPaterno(usuario.getApellidoPaterno());
					}
					if (usuario.getApellidoMaterno() != "") {
						userTemp.setApellidoMaterno(usuario.getApellidoMaterno());
					}
					if (usuario.getApellidoMaterno() != "") {
						userTemp.setApellidoMaterno(usuario.getApellidoMaterno());
					}
					if (!usuario.getRoles().isEmpty()) {
						userTemp.setRoles(usuario.getRoles());
					}
					if (imagen != null) {
						userTemp.setImagen(imagen.getBytes());
					}

					Usuario userUpdate = userService.guardar(userTemp);

					if (userUpdate != null) {
						userUpdate.setPassword("");

						return new ResponseEntity<Usuario>(userUpdate, null, HttpStatus.OK);

					}
					responseBody.put("mensaje", "El ID: "
							.concat(usuario.toString().concat(" no se pudo actualizar en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null,
							HttpStatus.INTERNAL_SERVER_ERROR);

				}

				responseBody.put("mensaje",
						"El ID: ".concat(usuario.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

			}
			responseBody.put("mensaje", "Datos inválidos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@DeleteMapping("{idUsuario}")
	public ResponseEntity<?> deleteUsuarioByIDUrl(
			@PathVariable(name = "idUsuario", required = true) String idUsuarioString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			Long idUsuario = Long.valueOf(idUsuarioString);

			Usuario userDeleted = userService.deleteReturn(idUsuario);

			if (userDeleted != null) {
				return new ResponseEntity<Usuario>(userDeleted, null, HttpStatus.OK);
			}

			responseBody.put("mensaje", "El ID: " + idUsuario + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ingrese un ID válido");
			responseBody.put("error",
					"NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID : " + idUsuarioString + " no existe en la base de datos");
			responseBody.put("error", "EmptyResultDataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "DataAccessException: "
					.concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

}
