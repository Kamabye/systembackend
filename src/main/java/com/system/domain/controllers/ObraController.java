package com.system.domain.controllers;

import java.util.HashMap;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.domain.interfaces.IObraService;
import com.system.domain.models.dto.ObraDTO;
import com.system.domain.models.postgresql.Obra;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping({ "apiv1/obra", "apiv1/obra/" })
@CrossOrigin(origins = { "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/", "https://kamabyeapp.onrender.com", "https://kamabyeapp.onrender.com/", "http://localhost:4200", "http://localhost:8080", "http://localhost:4200/", "http://localhost:8080/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS, RequestMethod.HEAD }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
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
	  @RequestParam(name = "string", required = false, defaultValue = "") String string) {
		
		Map<String, Object> responseBody = new HashMap<>();
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		
		try {
			Page<ObraDTO> obrasDTO;
			
			if (string != null && !string.isEmpty() && !string.isBlank()) {
				obrasDTO = obraService.jpqlfindByStringDTO(pageNumber, pageSize, string);
				return new ResponseEntity<Page<ObraDTO>>(obrasDTO, null, HttpStatus.OK);
			} else {
				obrasDTO = obraService.jpqlfindAllDTO(pageNumber, pageSize);
				if (!obrasDTO.isEmpty() && obrasDTO.hasContent() && obrasDTO.getSize() > 0 && obrasDTO.getTotalElements() > 0) {
					return new ResponseEntity<Page<ObraDTO>>(obrasDTO, null, HttpStatus.OK);
				}
				return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
			}
		} catch (EmptyResultDataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@GetMapping("{idObra}")
	public ResponseEntity<?> getObraByIDUrl(
	  @PathVariable(name = "idObra", required = true) String idObraString) {
		
		Map<String, Object> responseBody = new HashMap<>();
		try {
			
			if (idObraString != null && !idObraString.isEmpty() && !idObraString.isBlank()) {
				
				Long idObra = Long.valueOf(idObraString);
				
				ObraDTO obraSave = obraService.jpqlfindByIdObra(idObra);
				
				if (obraSave != null) {
					return new ResponseEntity<ObraDTO>(obraSave, null, HttpStatus.OK);
				}
				return new ResponseEntity<Map<String, Object>>(null, null, HttpStatus.NO_CONTENT);
			}
			
			responseBody.put("error", "ID : ".concat(idObraString).concat(" requerido"));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
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
			return new ResponseEntity<ObraDTO>(null, null, HttpStatus.NO_CONTENT);
			
		} catch (DataIntegrityViolationException e) {
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
	}
	
	@PutMapping("{idObra}")
	public ResponseEntity<?> updateObraByIDUrl(
	  @PathVariable(name = "idObra", required = true) String idObraString,
	  @RequestPart(name = "obraJSON", required = true) String obraJSON) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		ObraDTO obrasave;
		Obra obratemp;
		try {
			if (idObraString != null) {
				
				Long idObra = Long.valueOf(idObraString);
				
				if (obraJSON != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					Obra obra = objectMapper.readValue(obraJSON, Obra.class);
					
					obratemp = obraService.findById(idObra);
					
					if (obratemp != null) {
						obra.setIdObra(obratemp.getIdObra());
						obrasave = obraService.putDTO(obratemp);
						if (obrasave != null) {
							return new ResponseEntity<ObraDTO>(obrasave, null, HttpStatus.CREATED);
						}
					}
					return new ResponseEntity<ObraDTO>(null, null, HttpStatus.NO_CONTENT);
				}
				responseBody.put("error", "JSON requerido");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			}
			responseBody.put("error", "idObra inválido");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataIntegrityViolationException e) {
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@PutMapping("")
	public ResponseEntity<?> updateObraByIDParam(@RequestParam(name = "idObra", required = true) String idObraString,
	  @RequestPart(name = "obraJSON", required = true) String obraJSON) {
		Map<String, Object> responseBody = new HashMap<>();
		
		ObraDTO obrasave;
		Obra obratemp;
		try {
			if (idObraString != null) {
				
				Long idObra = Long.valueOf(idObraString);
				
				if (obraJSON != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					Obra obra = objectMapper.readValue(obraJSON, Obra.class);
					
					obratemp = obraService.findById(idObra);
					
					if (obratemp != null) {
						obra.setIdObra(obratemp.getIdObra());
						obrasave = obraService.putDTO(obratemp);
						if (obrasave != null) {
							return new ResponseEntity<ObraDTO>(obrasave, null, HttpStatus.CREATED);
						}
					}
					return new ResponseEntity<ObraDTO>(null, null, HttpStatus.NO_CONTENT);
				}
				responseBody.put("error", "JSON requerido");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			}
			responseBody.put("error", "idObra inválido");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataIntegrityViolationException e) {
			responseBody.put("error", "DataIntegrityViolationException: "
			  .concat(e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@PatchMapping("")
	public ResponseEntity<?> updateObraByIDUrlPatch(@RequestBody Obra obra) {
		Map<String, Object> responseBody = new HashMap<>();
		
		ObraDTO obratemp;
		try {
			
			obratemp = obraService.patchDTO(obra);
			return new ResponseEntity<ObraDTO>(obratemp, null, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@DeleteMapping("{idObra}")
	public ResponseEntity<?> deleteObraByIDUrl(
	  @PathVariable(name = "idObra", required = true) String idObraString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			if (idObraString != null) {
				Long idObra = Long.valueOf(idObraString);
				
				ObraDTO obraDelete = obraService.jpqlfindByIdObra(idObra);
				
				if (obraDelete != null) {
					
					obraService.delete(idObra);
					
					return new ResponseEntity<ObraDTO>(obraDelete, null, HttpStatus.OK);
				}
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
			}
			
			responseBody.put("error", "idObra requerido");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
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
				
				return new ResponseEntity<ObraDTO>(obraDelete, null, HttpStatus.OK);
			}
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
		} catch (NumberFormatException e) {
			responseBody.put("error",
			  "NumberFormatException: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			responseBody.put("error", "DataAccessException: "
			  .concat(e.getMostSpecificCause().getMessage().concat(" : ").concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("error", "Exception: ".concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
}
