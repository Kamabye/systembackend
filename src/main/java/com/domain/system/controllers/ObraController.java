package com.domain.system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.domain.system.interfaces.IObraService;
import com.domain.system.models.postgresql.Obra;

@Controller
@RequestMapping("apiv1/obra")
@CrossOrigin(origins = "http://localhost:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")
public class ObraController {

	@Autowired
	private IObraService obrasService;

	@GetMapping("")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> findAllObras(@RequestParam(name = "idObra", required = false) Long idObra) {
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		Map<String, Object> responseBody = new HashMap<>();
		// System.out.println("Id Obra = " +idObra.toString());
		if (idObra != null) {
			Obra obra = null;

			try {
				obra = obrasService.findById(idObra);
				if (obra == null) {
					responseBody.put("mensaje",
							"La Obra ID: ".concat(idObra.toString().concat(" no existe en la base de datos!.")));
					return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
				}

				return new ResponseEntity<Obra>(obra, null, HttpStatus.OK);
			} catch (DataAccessException e) {
				responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
				responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (Exception e) {
				responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
				responseBody.put("error", e.getMessage().concat(" : "));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
			} finally {

			}

		}
		List<Obra> listaObras;

		try {
			listaObras = obrasService.findAll();
			if (listaObras.size() > 0) {

				responseBody.put("mensaje", "Obras encontradas");
				responseBody.put("obras", listaObras);

				// responseHeaders.add("Accept-Encoding", "compress;q=0.5");
				// responseHeaders.add("Accept-Encoding", "gzip;q=1.0");

				// return new ResponseEntity<Map<String, Object>>(responseBody, responseHeaders,
				// HttpStatus.OK);

				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
			}
			responseBody.put("error", "Obras no encontradas");
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NO_CONTENT);
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

	@PostMapping("")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> saveObra(@RequestBody Obra obra) {
		Map<String, Object> responseBody = new HashMap<>();
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		Obra obraSave;

		try {
			obraSave = obrasService.save(obra);

			responseBody.put("mensaje", "Obras encontrados");
			responseBody.put("obra", obraSave);

			// responseHeaders.add("Accept-Encoding", "compress;q=0.5");
			// responseHeaders.add("Accept-Encoding", "gzip;q=1.0");

			// return new ResponseEntity<Map<String, Object>>(responseBody, responseHeaders,
			// HttpStatus.OK);

			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
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

	@GetMapping("/{idObra}")
	public ResponseEntity<?> getObraByIDUrl(@PathVariable("idObra") Long idObra) {

		Obra obra = null;
		Map<String, Object> responseBody = new HashMap<>();
		try {
			obra = obrasService.findById(idObra);
			if (obra == null) {
				responseBody.put("mensaje",
						"la Obra ID: ".concat(idObra.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Obra>(obra, null, HttpStatus.OK);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

	@GetMapping("/")
	public ResponseEntity<?> getUsersByIDParamSlash(@RequestParam("idObra") Long idObra) {
		// MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
		Map<String, Object> responseBody = new HashMap<>();

		Obra obra = null;

		try {
			obra = obrasService.findById(idObra);
			if (obra == null) {
				responseBody.put("mensaje",
						"La Obra ID: ".concat(idObra.toString().concat(" no existe en la base de datos!.")));
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Obra>(obra, null, HttpStatus.OK);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}

	}

}
