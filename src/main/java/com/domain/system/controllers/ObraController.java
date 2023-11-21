package com.domain.system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.domain.system.interfaces.IObraService;
import com.domain.system.models.dto.ObraDTO;
import com.domain.system.models.postgresql.Obra;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping({ "apiv1/obra", "apiv1/obra/" })
@CrossOrigin(origins = "http://localhost:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")
public class ObraController {

	@Autowired
	private IObraService obraService;

	/**
	 * 
	 * @param idObraString
	 * @return
	 */
	@GetMapping("")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> findAllObras(@RequestParam(name = "idObra", required = false) String idObraString,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "compositor", required = false) String compositor,
			@RequestParam(name = "arreglsita", required = false) String arreglista,
			@RequestParam(name = "letrista", required = false) String letrista,
			@RequestParam(name = "genero", required = false) String genero) {
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		Map<String, Object> responseBody = new HashMap<>();
		// System.out.println("Id Obra = " +idObra.toString());

		try {
			if (idObraString != null) {

				Long idObra = Long.valueOf(idObraString);

				ObraDTO obra = obraService.jpqlfindByIdObra(idObra);

				if (obra == null) {
					responseBody.put("mensaje",
							"La Obra ID: ".concat(idObraString.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
				}
				responseBody.put("obra", obra);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);

			}
			// Validar cada uno de los parámetros por separado
			if (nombre != null) {

				Set<ObraDTO> obras = obraService.jpqlfindByNombre(nombre);

				if (obras.isEmpty()) {
					responseBody.put("mensaje", "La Obra con nombre : "
							.concat(nombre.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				}
				responseBody.put("obras", obras);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);

			}

			if (compositor != null) {

				Set<ObraDTO> obras = obraService.jpqlfindByCompositor(compositor);

				if (obras.isEmpty()) {
					responseBody.put("mensaje", "La Obra del compositor : "
							.concat(compositor.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				}
				responseBody.put("obras", obras);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);

			}

			if (arreglista != null) {

				Set<ObraDTO> obras = obraService.jpqlfindByArreglista(arreglista);

				if (obras.isEmpty()) {
					responseBody.put("mensaje", "La Obra del arreglista : "
							.concat(arreglista.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				}
				responseBody.put("obras", obras);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);

			}

			if (letrista != null) {

				Set<ObraDTO> obras = obraService.jpqlfindByLetrista(letrista);

				if (obras.isEmpty()) {
					responseBody.put("mensaje", "La Obra del letrista : "
							.concat(letrista.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				}
				responseBody.put("obra", obras);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);

			}

			if (genero != null) {

				Set<ObraDTO> obras = obraService.jpqlfindByGenero(genero);

				if (obras.isEmpty()) {
					responseBody.put("mensaje", "La Obra del genero : "
							.concat(genero.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				}
				responseBody.put("obra", obras);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);

			}

			// Al final si ningún parámetro existe o es válido, se cargan todas las obras
			// List<Obra> listaObras = obrasService.findAll();
			List<ObraDTO> listaObras = obraService.jpqlfindAll();
			if (!listaObras.isEmpty()) {
				// responseBody.put("mensaje", "Obras encontradas");
				responseBody.put("obras", listaObras);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
			}
			responseBody.put("error", "Sin datos encontrados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@PostMapping("")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> saveObra(@RequestBody Obra obra,
			@RequestParam(name = "audio", required = false) MultipartFile audio) {
		Map<String, Object> responseBody = new HashMap<>();
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		Obra obraSave;

		try {

			if (audio != null) {
				obra.setAudioFromInputStream(audio.getInputStream());
			}
			obraSave = obraService.save(obra);

			ObraDTO obraDTO = new ObraDTO(obraSave.getId(), obraSave.getNombre(), obraSave.getCompositor(),
					obraSave.getArreglista(), obraSave.getLetrista(), obraSave.getGenero(), obraSave.getPrecio());

			responseBody.put("obraDTO", obraDTO);
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);

		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al guardar en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@PostMapping("upload")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> uploadObra(@RequestPart(name = "obraJSON", required = true) String obraJSON,
			@RequestPart(name = "audio", required = true) MultipartFile audio) {
		Map<String, Object> responseBody = new HashMap<>();
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		Obra obraSave;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Obra obra = objectMapper.readValue(obraJSON, Obra.class);

			if (audio != null) {
				obra.setAudioFromInputStream(audio.getInputStream());
			}
			obraSave = obraService.save(obra);

			ObraDTO obraDTO = new ObraDTO(obraSave.getId(), obraSave.getNombre(), obraSave.getCompositor(),
					obraSave.getArreglista(), obraSave.getLetrista(), obraSave.getGenero(), obraSave.getPrecio());

			responseBody.put("obraDTO", obraDTO);
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);

		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FileSizeLimitExceededException e) {
			responseBody.put("mensaje",
					"Error al guardar en la base de datos. Límite excedido FileSizeLimitExceededException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@GetMapping("{idObra}")
	public ResponseEntity<?> getObraByIDUrl(@PathVariable(name = "idObra", required = true) String idObraString) {

		ObraDTO obraSave = null;
		Map<String, Object> responseBody = new HashMap<>();
		try {

			if (idObraString != null) {

				Long idObra = Long.valueOf(idObraString);

				obraSave = obraService.jpqlfindByIdObra(idObra);
				if (obraSave != null) {
					responseBody.put("obraDTO", obraSave);
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
				}
				responseBody.put("mensaje",
						"la Obra ID: ".concat(idObraString.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			}

			responseBody.put("mensaje", "ID : ".concat(idObraString).concat(" inválido"));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

	@GetMapping("play/{idObra}")
	public ResponseEntity<?> playObra(@PathVariable(name = "idObra", required = true) String idObraString) {

		ObraDTO obraSave = null;
		Map<String, Object> responseBody = new HashMap<>();
		try {

			if (idObraString != null) {
				Long idObra = Long.valueOf(idObraString);
				
				Obra obra = obraService.findById(idObra);


				ByteArrayResource resource = new ByteArrayResource(obra.getAudio());

		        // Configura los encabezados de la respuesta
		        HttpHeaders headers = new HttpHeaders();
		        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + obra.getNombre() + ".aac");
		        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

		        // Devuelve la respuesta con el recurso y los encabezados
		        return ResponseEntity.ok()
		                .headers(headers)
		                .contentLength(resource.contentLength())
		                .contentType(MediaType.APPLICATION_OCTET_STREAM)
		                .body(resource);
		        
		        
			}

			responseBody.put("mensaje", "ID : ".concat(idObraString).concat(" inválido"));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

	@PutMapping("{idObra}")
	public ResponseEntity<?> updateObraByIDUrl(@PathVariable("idObra") Long idObra, @RequestBody Obra obra,
			@RequestParam(name = "audio", required = false) MultipartFile audio) {
		Map<String, Object> responseBody = new HashMap<>();
		Obra obrasave;
		Obra obratemp;
		ObraDTO obraDTO;
		try {
			obratemp = obraService.findById(idObra);

			if (obratemp != null) {
				if (obra.getNombre() != null)
					obratemp.setNombre(obra.getNombre());
				if (obra.getCompositor() != null)
					obratemp.setCompositor(obra.getCompositor());
				if (obra.getArreglista() != null)
					obratemp.setArreglista(obra.getArreglista());
				if (obra.getLetrista() != null)
					obratemp.setLetrista(obra.getLetrista());
				if (obra.getPrecio() != null)
					obratemp.setPrecio(obra.getPrecio());
				if (obra.getGenero() != null)
					obratemp.setGenero(obra.getGenero());
				if (!audio.isEmpty()) {
					obratemp.setAudioFromInputStream(audio.getInputStream());
				}

				obrasave = obraService.save(obratemp);
				obraDTO = obraService.jpqlfindByIdObra(obrasave.getId());
				responseBody.put("mensaje", "La obra : " + idObra + " ha sido actualizado con éxito");
				responseBody.put("obraDTO", obraDTO);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
			}
			responseBody.put("mensaje", "La obra : " + idObra + " no existe sido actualizado con éxito");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);

		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

	@PutMapping("")
	public ResponseEntity<?> updateObraByIDParam(@RequestParam("idObra") Long idObra, @RequestBody Obra obra,
			@RequestParam(name = "audio", required = false) MultipartFile audio) {
		Map<String, Object> responseBody = new HashMap<>();
		Obra obrasave;
		Obra obratemp;
		ObraDTO obraDTO;

		try {
			obratemp = obraService.findById(idObra);

			if (obra.getNombre() != null)
				obratemp.setNombre(obra.getNombre());
			if (obra.getCompositor() != null)
				obratemp.setCompositor(obra.getCompositor());
			if (obra.getArreglista() != null)
				obratemp.setArreglista(obra.getArreglista());
			if (obra.getLetrista() != null)
				obratemp.setLetrista(obra.getLetrista());
			if (obra.getPrecio() != null)
				obratemp.setPrecio(obra.getPrecio());
			if (obra.getGenero() != null)
				obratemp.setGenero(obra.getGenero());
			if (!audio.isEmpty()) {
				obratemp.setAudioFromInputStream(audio.getInputStream());
			}

			obrasave = obraService.save(obratemp);
			obraDTO = obraService.jpqlfindByIdObra(obrasave.getId());
			responseBody.put("mensaje", "La obra : " + idObra + " ha sido actualizado con éxito");
			responseBody.put("obraDTO", obraDTO);
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

	@DeleteMapping("{idObra}")
	public ResponseEntity<?> deleteObraByIDUrl(@PathVariable("idObra") Long idObra) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			obraService.delete(idObra);

			responseBody.put("mensaje", "La obra : " + idObra + " ha sido eliminada con éxito");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteObraByIDParam(@RequestParam("idObra") Long idObra) {
		Map<String, Object> responseBody = new HashMap<>();

		try {
			obraService.delete(idObra);
			responseBody.put("mensaje", "La obra : " + idObra + " ha sido eliminado con éxito");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

}
