package com.system.domain.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.system.domain.interfaces.IObraService;
import com.system.domain.interfaces.IPDFService;
import com.system.domain.interfaces.IPartituraService;
import com.system.domain.models.dto.LobDTO;
import com.system.domain.models.dto.ObraDTO;
import com.system.domain.models.dto.PartituraDTO;
import com.system.domain.models.postgresql.Obra;
import com.system.domain.models.postgresql.Partitura;

@RestController
@RequestMapping({ "apiv1/partitura", "apiv1/partitura/" })
@CrossOrigin(origins = { "https://system-i73z.onrender.com", "https://system-i73z.onrender.com/", "https://opticalemus.onrender.com", "https://opticalemus.onrender.com/", "https://kamabyeapp.onrender.com", "https://kamabyeapp.onrender.com/", "http://localhost:4200", "http://localhost:8080", "http://localhost:4200/", "http://localhost:8080/" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS }, allowedHeaders = { "Authorization", "Content-Type" }, exposedHeaders = {})
public class PartiturasController {
	
	@Autowired
	IPartituraService partituraService;
	
	@Autowired
	IObraService obraService;
	
	@Autowired
	IPDFService pdfService;
	
	@GetMapping("")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> findAllPartituras(@RequestParam(name = "idObra", required = false) String idObraString) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			if (idObraString != null) {
				Long idObra = Long.valueOf(idObraString);
				
				ObraDTO obraDTO = partituraService.jpqlfindObraByIdObra(idObra);
				
				responseBody.put("ObraDTO", obraDTO);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
			}
			
			responseBody.put("mensaje", "La Obra ID: ".concat("debe ser ingresado"));
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
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos Exception");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}
		
	}
	
	@PostMapping("{idObra}")
	public ResponseEntity<?> savePartitura(
			@RequestBody Partitura partitura,
			@PathVariable(name = "idObra", required = true) String idObraString) {
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			Long idObra = Long.valueOf(idObraString);
			
			Obra obra = new Obra();
			obra.setIdObra(idObra);
			
			partitura.setObra(obra);
			
			PartituraDTO partituraSave = partituraService.saveDTO(partitura);
			
			if (partituraSave != null) {
				return new ResponseEntity<PartituraDTO>(partituraSave, null, HttpStatus.CREATED);
			}
			
			responseBody.put("mensaje",
			  "La Partitura :".concat(partitura.toString().concat(" no se pudo guardar en la base de datos!.")));
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
			responseBody.put("mensaje", "El Objeto : ".concat(partitura.getInstrumento().concat(" ya existe en la base de datos")));
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
	
	@PatchMapping("patch2")
	public ResponseEntity<?> uploadPartitura2(
	  // @RequestPart(name = "partituraPDF", required = false) MultipartFile
	  // partituraPDF,
	  @RequestParam(name = "partituraPDF", required = false) MultipartFile partituraPDF,
	  @RequestParam(name = "idObra", required = false) Long idObraString,
	  @RequestParam(name = "instrumento", required = false) String instrumento) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		try {
			if (idObraString != null && instrumento != null) {
				
				Long idObra = Long.valueOf(idObraString);
				
				Obra obra = obraService.findById(idObra);
				
				if (obra != null) {
					
					Set<String> instrumentos = partituraService.jpqlfindInstrumentos(idObra);
					boolean bandera = false;
					if (instrumentos != null) {
						
						for (String ins : instrumentos) {
							if (ins.equals(instrumento)) {
								bandera = true;
							}
						}
					}
					
					if (!bandera) {
						System.out.println("Bandera falsa");
						
						if (pdfService.isPDFValid(partituraPDF)) {
							
							System.out.println("ContentType: " + partituraPDF.getContentType());
							System.out.println("Name: " + partituraPDF.getName());
							System.out.println("OriginalFileName: " + partituraPDF.getOriginalFilename());
							System.out.println("Size: " + partituraPDF.getSize());
							
							byte[] vistaPrevia = pdfService.ponerMarcaAgua(partituraPDF);
							if (vistaPrevia.length > 0) {
								Partitura partitura = new Partitura();
								partitura.setInstrumento(instrumento);
								partitura.setPartituraPDFFromInputStream(partituraPDF.getInputStream());
								partitura.setVistaPreviaPDF(vistaPrevia);
								partitura.setObra(obra);
								partituraService.save(partitura);
								
								responseBody.put("obraDTO", partituraService.jpqlfindObraByIdObra(idObra));
								return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
							}
							responseBody.put("mensaje", "Error en vistaprevia");
							return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
							
						}
						responseBody.put("mensaje", "Partitura inválida");
						return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
						
					}
					
					responseBody.put("mensaje", "Instrumento duplicado");
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
					
				}
			}
			
			responseBody.put("mensaje", "Debe ingresar todos los campos solicitados");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (HibernateException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos HibernateException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			responseBody.put("mensaje", "Error en la lectura del archivo PDF");
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
	
	@PatchMapping("/upload/{idPartitura}")
	public ResponseEntity<?> uploadPartitura(
	  @PathVariable(name = "idPartitura", required = true) String idPartituraString,
	  @RequestParam(name = "partituraPDF", required = true) MultipartFile partituraPDF) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		try (InputStream inputStream = partituraPDF.getInputStream()) {
			if (pdfService.isPDFValid(partituraPDF)) {
				Long idPartitura = Long.valueOf(idPartituraString);
				
				Partitura partitura = partituraService.findById(idPartitura);
				
				byte[] vistaPrevia = pdfService.ponerMarcaAgua(inputStream);
				partitura.setPartituraPDFFromInputStream(inputStream);
				partitura.setVistaPreviaPDF(vistaPrevia);
				
				PartituraDTO partituraSave = partituraService.saveDTO(partitura);
				
				//
				//ByteArrayResource resource = new ByteArrayResource(partituraSave.getVistaPreviaPDF());
				
				//HttpHeaders headers = new HttpHeaders();
				
				//headers.setContentType(MediaType.APPLICATION_PDF);
				//headers.add("Content-Disposition","inline; filename=" + partituraSave.getObra().getNombre() + "_" + partituraSave.getInstrumento() + ".pdf");
				//headers.setContentLength(resource.contentLength());
				
				//return ResponseEntity.ok().headers(headers).body(resource);
				return new ResponseEntity<PartituraDTO>(partituraSave, null, HttpStatus.CREATED);
				
				
			}
			responseBody.put("mensaje", "Partitura inválida");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (HibernateException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos HibernateException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos DataAccessException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			responseBody.put("mensaje", "Error en la lectura del archivo PDF");
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
	
	@PutMapping("{idPartitura}")
	public ResponseEntity<?> updatePartitura(
	  @PathVariable(name = "idPartitura", required = false) String idPartituraString) {
		
		try {
			return null;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@GetMapping("view/{idPartitura}")
	public ResponseEntity<?> findPartituraByIDURL(
	  @PathVariable(name = "idPartitura", required = false) String idPartituraString) {
		
		Map<String, Object> responseBody = new HashMap<>();
		try {
			
			if (idPartituraString != null) {
				
				Partitura temp;
				
				Long idPartitura = Long.valueOf(idPartituraString);
				
				temp = partituraService.findById(idPartitura);
				
				if (temp != null) {
					
					ByteArrayResource resource = new ByteArrayResource(temp.getVistaPreviaPDF());
					
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_PDF);
					headers.add("Content-Disposition",
					  "inline; filename=" + temp.getObra().getNombre() + "_" + temp.getInstrumento() + ".pdf");
					headers.setContentLength(resource.contentLength());
					// headers.set("X-Frame-Options", "ALLOW-FROM http://localhost:4200"); // Set
					// the X-Frame-Options header
					
					return ResponseEntity.ok().headers(headers).body(resource);
				}
				responseBody.put("mensaje", "El ID: " + idPartitura + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("mensaje", "El idPartitura debe ser ingresado");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		}
		
		catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (HibernateException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos HibernateException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
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
	
	@GetMapping("download/{idObra}")
	public ResponseEntity<?> downloadPartiturasByIDObraURL(
	  @PathVariable(name = "idObra", required = false) String idObraString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			
			if (idObraString != null) {
				
				Long idObra = Long.valueOf(idObraString);
				
				Obra obra = obraService.findById(idObra);
				
				List<LobDTO> partituras = partituraService.jpqlLobFindByIdObra(idObra);
				
				if (partituras != null) {
					ByteArrayResource resource = new ByteArrayResource(pdfService.unirPDF(partituras));
					
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_PDF);
					headers.setContentLength(resource.contentLength());
					// headers.setContentDispositionFormData("attachment",temp.getObra().getNombre()
					// + "_" + temp.getInstrumento() + ".pdf");
					
					headers.setContentDispositionFormData("inline", obra.getNombre() + ".pdf");
					
					return new ResponseEntity<>(resource, headers, HttpStatus.OK);
				}
				responseBody.put("mensaje", "El ID: " + idObraString + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("mensaje", "El idObra debe ser ingresado");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		}
		
		catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (HibernateException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos HibernateException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
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
	
	@DeleteMapping("{idPartitura}")
	public ResponseEntity<?> deletePartituraByIDURL(
	  @PathVariable(name = "idPartitura", required = false) String idPartituraString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			
			if (idPartituraString != null) {
				
				PartituraDTO partituraDTO;
				
				Long idPartitura = Long.valueOf(idPartituraString);
				
				partituraDTO = partituraService.jpqlfindById(idPartitura);
				
				if (partituraDTO != null) {
					
					partituraService.delete(idPartitura);
					
					responseBody.put("mensaje", "La partitura : " + idPartitura + " ha sido eliminada con éxito");
					responseBody.put("PartituraDTO", partituraDTO);
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
				}
				responseBody.put("mensaje", "El ID: " + idPartitura + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("mensaje", "El idPartitura debe ser ingresado");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		}
		
		catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (HibernateException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos HibernateException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
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
	
	@DeleteMapping("")
	public ResponseEntity<?> deletePartituraByIDParam(
	  @RequestParam(name = "idPartitura", required = false) String idPartituraString) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			
			if (idPartituraString != null) {
				
				PartituraDTO partituraDTO;
				
				Long idPartitura = Long.valueOf(idPartituraString);
				
				partituraDTO = partituraService.jpqlfindById(idPartitura);
				
				if (partituraDTO != null) {
					
					partituraService.delete(idPartitura);
					
					responseBody.put("mensaje", "La partitura : " + idPartitura + " ha sido eliminada con éxito");
					responseBody.put("PartituraDTO", partituraDTO);
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
				}
				responseBody.put("mensaje", "El ID: " + idPartitura + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("mensaje", "El idPartitura debe ser ingresado");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		}
		
		catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (HibernateException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos HibernateException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
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
	
	@GetMapping("related/{idObra}")
	public ResponseEntity<?> findPartiturasByIDObra(
	  @PathVariable(name = "idObra", required = true) String idObraString) {
		
		Map<String, Object> responseBody = new HashMap<>();
		try {
			
			if (idObraString != null) {
				
				Set<PartituraDTO> temp;
				
				Long idPartitura = Long.valueOf(idObraString);
				
				temp = partituraService.jpqlfindByIdObra(idPartitura);
				
				if (temp != null) {
					
					return new ResponseEntity<Set<PartituraDTO>>(temp, null, HttpStatus.OK);
				}
				responseBody.put("mensaje", "El ID: " + idPartitura + " no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				
			}
			responseBody.put("mensaje", "El idPartitura debe ser ingresado");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
			
		}
		
		catch (NumberFormatException e) {
			// e.printStackTrace();
			responseBody.put("mensaje", "El ID no es válido NumberFormatException");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.BAD_REQUEST);
		} catch (HibernateException e) {
			e.printStackTrace();
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos HibernateException");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
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
	
}
