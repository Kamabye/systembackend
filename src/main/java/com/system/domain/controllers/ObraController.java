package com.system.domain.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.domain.interfaces.IObraService;
import com.system.domain.models.dto.ObraDTO;
import com.system.domain.models.postgresql.Obra;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping({ "apiv1/obra", "apiv1/obra/" })
@CrossOrigin(origins = { "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/", "https://kamabyeapp.onrender.com", "https://kamabyeapp.onrender.com/", "http://localhost:4200", "http://localhost:8080", "http://localhost:4200/", "http://localhost:8080/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
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
	public ResponseEntity<?> findAllObras(
	  @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
	  @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize,
	  @RequestParam(name = "string", required = false, defaultValue = "") String string,
	  @RequestParam(name = "nombre", required = false) String nombre,
	  @RequestParam(name = "compositor", required = false) String compositor,
	  @RequestParam(name = "arreglsita", required = false) String arreglista,
	  @RequestParam(name = "letrista", required = false) String letrista,
	  @RequestParam(name = "genero", required = false) String genero) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			Page<ObraDTO> obrasDTO;
			
			if (string != null && !string.isEmpty() && !string.isBlank()) {
				obrasDTO = obraService.jpqlfindByStringDTO(pageNumber, pageSize, string);
				System.out.println("Se va a retornar obras");
				return new ResponseEntity<Page<ObraDTO>>(obrasDTO, null, HttpStatus.OK);
			} else {
				obrasDTO = obraService.jpqlfindAllDTO(pageNumber, pageSize);
				if (!obrasDTO.isEmpty() && obrasDTO.hasContent() && obrasDTO.getSize() > 0 && obrasDTO.getTotalElements() > 0) {
					return new ResponseEntity<Page<ObraDTO>>(obrasDTO, null, HttpStatus.OK);
				}
				return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
			}
			
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@GetMapping("{idObra}")
	public ResponseEntity<?> getObraByIDUrl(@PathVariable(name = "idObra", required = true) String idObraString) {
		
		Map<String, Object> responseBody = new HashMap<>();
		try {
			
			if (idObraString != null) {
				
				Long idObra = Long.valueOf(idObraString);
				
				ObraDTO obraSave = null;
				
				obraSave = obraService.jpqlfindByIdObra(idObra);
				if (obraSave != null) {
					// responseBody.put("obraDTO", obraSave);
					// return new ResponseEntity<Map<String, Object>>(responseBody, null,
					// HttpStatus.OK);
					return new ResponseEntity<ObraDTO>(obraSave, null, HttpStatus.OK);
				}
				responseBody.put("mensaje",
				  "la Obra ID: ".concat(idObraString.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			}
			
			responseBody.put("mensaje", "ID : ".concat(idObraString).concat(" requerido"));
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
	
	@PostMapping("")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> saveObra(@RequestBody Obra obra) {
		Map<String, Object> responseBody = new HashMap<>();
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		
		try {
			
			ObraDTO obraSave = obraService.saveDTO(obra);
			
			if (obraSave != null) {
				return new ResponseEntity<ObraDTO>(obraSave, null, HttpStatus.CREATED);
			}
			
			responseBody.put("mensaje",
			  "La Obra :".concat(obra.toString().concat(" no se pudo guardar en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (MultipartException e) {
			responseBody.put("mensaje", "Error al realizar la inserción en la base de datos MultipartException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataIntegrityViolationException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El Objeto : ".concat(obra.getNombre().concat(" ya existe en la base de datos")));
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la inserción en la base de datos DataAccessException");
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
	
	@PutMapping("{idObra}")
	public ResponseEntity<?> updateObraByIDUrl(@PathVariable(name = "idObra", required = true) String idObraString,
	  @RequestPart(name = "obraJSON", required = true) String obraJSON,
	  @RequestPart(name = "audio", required = false) MultipartFile audio) {
		Map<String, Object> responseBody = new HashMap<>();
		
		Obra obrasave;
		Obra obratemp;
		try {
			if (idObraString != null) {
				
				Long idObra = Long.valueOf(idObraString);
				
				if (obraJSON != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					Obra obraDTO = objectMapper.readValue(obraJSON, Obra.class);
					
					obratemp = obraService.findById(idObra);
					
					if (obratemp != null) {
						if (obraDTO.getNombre() != null)
							obratemp.setNombre(obraDTO.getNombre());
						if (obraDTO.getCompositor() != null)
							obratemp.setCompositor(obraDTO.getCompositor());
						if (obraDTO.getArreglista() != null)
							obratemp.setArreglista(obraDTO.getArreglista());
						if (obraDTO.getLetrista() != null)
							obratemp.setLetrista(obraDTO.getLetrista());
						if (obraDTO.getPrecio() != null)
							obratemp.setPrecio(obraDTO.getPrecio());
						if (obraDTO.getGenero() != null)
							obratemp.setGenero(obraDTO.getGenero());
						if (obraDTO.getEmbedAudio() != null) {
							obratemp.setEmbedAudio(obraDTO.getEmbedAudio());
						}
						
						if (obraDTO.getEmbedVideo() != null) {
							obratemp.setEmbedVideo(obraDTO.getEmbedVideo());
						}
						if (audio != null) {
							obratemp.setAudioFromInputStream(audio.getInputStream());
						}
						
						obrasave = obraService.save(obratemp);
						ObraDTO obraDTOupdate = obraService.jpqlfindByIdObra(obrasave.getIdObra());
						// responseBody.put("mensaje", "La obra : " + idObra + " ha sido actualizado con
						// éxito");
						// responseBody.put("obraDTO", obraDTO);
						// return new ResponseEntity<Map<String, Object>>(responseBody, null,
						// HttpStatus.CREATED);
						return new ResponseEntity<ObraDTO>(obraDTOupdate, null, HttpStatus.CREATED);
					}
					responseBody.put("mensaje", "La obra : " + idObra + " no existe");
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				}
				responseBody.put("mensaje", "Parámetros inválidos nulos");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
				
			}
			responseBody.put("mensaje", "Parámetros inválidos nulos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (InvalidContentTypeException e) {
			responseBody.put("mensaje", "Error en el multipart form InvalidContentTypeException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MultipartException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos MultipartException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataIntegrityViolationException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El Objeto :".concat(obraJSON.concat(" ya existe en la base de datos")));
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FileSizeLimitExceededException e) {
			responseBody.put("mensaje",
			  "Error al guardar en la base de datos. Límite excedido FileSizeLimitExceededException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "IOException: ".concat(e.getMessage()));
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
	public ResponseEntity<?> updateObraByIDParam(@RequestParam(name = "idObra", required = true) String idObraString,
	  @RequestPart(name = "obraJSON", required = true) String obraJSON,
	  @RequestPart(name = "audio", required = false) MultipartFile audio) {
		Map<String, Object> responseBody = new HashMap<>();
		
		Obra obrasave;
		Obra obratemp;
		ObraDTO obraDTO;
		try {
			if (idObraString != null) {
				
				Long idObra = Long.valueOf(idObraString);
				
				ObjectMapper objectMapper = new ObjectMapper();
				Obra obra = objectMapper.readValue(obraJSON, Obra.class);
				
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
					if (audio != null) {
						obratemp.setAudioFromInputStream(audio.getInputStream());
					}
					
					obrasave = obraService.save(obratemp);
					obraDTO = obraService.jpqlfindByIdObra(obrasave.getIdObra());
					// responseBody.put("mensaje", "La obra : " + idObra + " ha sido actualizado con
					// éxito");
					responseBody.put("obraDTO", obraDTO);
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
				}
				responseBody.put("mensaje", "La obra : " + idObra + " no existe");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			}
			responseBody.put("mensaje", "Parámetros inválidos nulos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (InvalidContentTypeException e) {
			responseBody.put("mensaje", "Error en el multipart form InvalidContentTypeException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MultipartException e) {
			responseBody.put("mensaje", "Error al realizar la eliminación en la base de datos MultipartException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
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
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@PatchMapping("/upload/{idObra}")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> saveObra(
	  @PathVariable(name = "idObra", required = true) String idObraString,
	  @RequestParam MultipartFile audioFile) {
		Map<String, Object> responseBody = new HashMap<>();
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		
		try (InputStream inputStream = audioFile.getInputStream()) {
			
			Long idObra = Long.valueOf(idObraString);
			
			Obra obra = obraService.findById(idObra);
			
			obra.setAudioFromInputStream(inputStream);
			
			Obra obrasave = obraService.save(obra);
			
			return new ResponseEntity<Obra>(obrasave, null, HttpStatus.OK);
			
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (MultipartException e) {
			responseBody.put("mensaje", "Error al realizar la inserción en la base de datos MultipartException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la inserción en la base de datos DataAccessException");
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
	
	@DeleteMapping("{idObra}")
	public ResponseEntity<?> deleteObraByIDUrl(@PathVariable(name = "idObra", required = true) String idObraString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			if (idObraString != null) {
				Long idObra = Long.valueOf(idObraString);
				System.out.println("IDObra a Borrar");
				
				ObraDTO obraDelete = obraService.jpqlfindByIdObra(idObra);
				
				//System.out.println("Se encontró la obra para borrarla");
				if (obraDelete != null) {
					
					obraService.delete(idObra);

					//System.out.println("Se borró la obra");
					return new ResponseEntity<ObraDTO>(obraDelete, null, HttpStatus.OK);
				}
				responseBody.put("mensaje", "La obra : " + idObra + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			}
			
			responseBody.put("mensaje", "Parámetros inválidos nulos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: ".concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> deleteObraByIDParam(@RequestParam(name = "idObra", required = true) String idObraString) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			Long idObra = Long.valueOf(idObraString);
			
			ObraDTO obraDelete = obraService.jpqlfindByIdObra(idObra);
			if (obraDelete != null) {
				
				obraService.delete(idObra);
				
				responseBody.put("mensaje", "La obra : " + idObra + " ha sido eliminada con éxito");
				responseBody.put("obraDTO", obraDelete);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			}
			responseBody.put("mensaje", "La obra : " + idObra + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@PutMapping("/upload/{idObra}")
	public ResponseEntity<?> uploadAudio(@PathVariable(name = "idObra", required = true) String idObraString,
	  @RequestPart(name = "audio", required = false) MultipartFile audio) {
		Map<String, Object> responseBody = new HashMap<>();
		
		Obra obrasave;
		Obra obratemp;
		try {
			if (idObraString != null) {
				
				Long idObra = Long.valueOf(idObraString);
				
				obratemp = obraService.findById(idObra);
				if (audio != null) {
					obratemp.setAudioFromInputStream(audio.getInputStream());
				}
				
				obrasave = obraService.save(obratemp);
				ObraDTO obraDTOupdate = obraService.jpqlfindByIdObra(obrasave.getIdObra());
				// responseBody.put("mensaje", "La obra : " + idObra + " ha sido actualizado con
				// éxito");
				// responseBody.put("obraDTO", obraDTO);
				// return new ResponseEntity<Map<String, Object>>(responseBody, null,
				// HttpStatus.CREATED);
				return new ResponseEntity<ObraDTO>(obraDTOupdate, null, HttpStatus.CREATED);
			}
			responseBody.put("mensaje", "Parámetros inválidos nulos");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		} catch (
		
		NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (InvalidContentTypeException e) {
			responseBody.put("mensaje", "Error en el multipart form InvalidContentTypeException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MultipartException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos MultipartException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FileSizeLimitExceededException e) {
			responseBody.put("mensaje",
			  "Error al guardar en la base de datos. Límite excedido FileSizeLimitExceededException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "Ha ocurrido un error.");
			responseBody.put("error", "IOException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@GetMapping("play/{idObra}")
	public ResponseEntity<?> playObra(@PathVariable(name = "idObra", required = true) String idObraString) {
		
		// Resource resourceAudio = new ClassPathResource("audio/sample.mp3");
		Map<String, Object> responseBody = new HashMap<>();
		try {
			
			if (idObraString != null) {
				Long idObra = Long.valueOf(idObraString);
				
				Obra obra = obraService.findById(idObra);
				
				ByteArrayResource resource = new ByteArrayResource(obra.getAudio());
				
				// Configura los encabezados de la respuesta
				HttpHeaders headers = new HttpHeaders();
				
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentLength(resource.contentLength());
				headers.setContentDispositionFormData("inline", obra.getNombre() + ".aac");
				
				// Devuelve la respuesta con el recurso y los encabezados
				// return ResponseEntity.ok().headers(headers).body(resource);
				
				return new ResponseEntity<>(resource, headers, HttpStatus.OK);
				
			}
			
			responseBody.put("mensaje", "ID : ".concat(idObraString).concat(" inválido"));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos DataAccessException");
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
