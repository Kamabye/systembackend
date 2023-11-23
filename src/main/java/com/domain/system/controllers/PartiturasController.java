package com.domain.system.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.domain.system.interfaces.IObraService;
import com.domain.system.interfaces.IPDFService;
import com.domain.system.interfaces.IPartituraService;
import com.domain.system.models.dto.ObraDTO;
import com.domain.system.models.postgresql.Obra;
import com.domain.system.models.postgresql.Partitura;

@RestController
@RequestMapping({ "apiv1/partitura", "apiv1/partitura/" })
@CrossOrigin(origins = "http://localhost:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")
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

	@PostMapping("")
	public ResponseEntity<?> uploadPartitura(
			@RequestParam(name = "partituraPDF", required = false) MultipartFile partituraPDF,
			@RequestParam(name = "idObra", required = false) Long idObraString,
			@RequestParam(name = "instrumento", required = false) String instrumento) {

		Map<String, Object> responseBody = new HashMap<>();

		try {
			if (!partituraPDF.isEmpty() && idObraString != null && instrumento != null) {

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
						byte[] vistaPrevia = pdfService.ponerMarcaAgua(partituraPDF);

						System.out.println("Se va a guardar la parttitura");
						Partitura partitura = new Partitura();
						partitura.setInstrumento(instrumento);
						System.out.println("Se va a cargará la parttitura");
						partitura.setPartituraPDFFromInputStream(partituraPDF.getInputStream());
						System.out.println("Se va a cargará la visa previa la parttitura");
						partitura.setVistaPreviaPDF(vistaPrevia);
						System.out.println("Se cargó la vista revia la partitura");
						partitura.setObra(obra);
						partituraService.save(partitura);
						System.out.println("Se guardo la partitura");

						responseBody.put("obraDTO", partituraService.jpqlfindObraByIdObra(idObra));
						return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
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

	@GetMapping("download/{idPartitura}")
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
					headers.setContentLength(resource.contentLength());
					// headers.setContentDispositionFormData("attachment",temp.getObra().getNombre()
					// + "_" + temp.getInstrumento() + ".pdf");

					headers.setContentDispositionFormData("inline",
							temp.getObra().getNombre() + "_" + temp.getInstrumento() + ".pdf");

					return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
