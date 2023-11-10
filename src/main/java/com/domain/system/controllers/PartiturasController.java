package com.domain.system.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
import com.domain.system.interfaces.IPartituraService;
import com.domain.system.models.dto.PartituraDTO;
import com.domain.system.models.postgresql.Obra;
import com.domain.system.models.postgresql.Partitura;

@RestController
@RequestMapping("apiv1/partitura")
@CrossOrigin(origins = "http://localhost:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")
public class PartiturasController {

	@Autowired
	IPartituraService partituraService;

	@Autowired
	IObraService obraService;

	@GetMapping("")
	// @PreAuthorize("hasAnyRole('Administrador', 'Editor', 'Lector',
	// 'USERS_Administrador', 'USERS_Editor', 'USERS_Lector')")
	public ResponseEntity<?> findAllPartituras(@RequestParam(name = "idObra", required = false) Long idObra) {
		Map<String, Object> responseBody = new HashMap<>();
		List<PartituraDTO> partituras;
		List<PartituraDTO> partiturasDTO;
		// List<Partitura> partituras2;

		try {
			if (idObra == null) {
				// partituras = partituraService.findAll();
				partituras = partituraService.jpqlFindAll();
				responseBody.put("partituras por IPartiturasService", partituras);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
			}

			if (idObra != null && idObra > 0) {

				partiturasDTO = partituraService.jpqlFindByObra(idObra);
				// partituras2 = obraService.findById(idObra).getPartituras();
				responseBody.put("partituras por IPartiturasService son idObra", partiturasDTO);
				// responseBody.put("partituras por IObrasService",partituras2);
				return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.OK);
			}

			responseBody.put("mensaje",
					"La Obra ID: ".concat(idObra.toString().concat(" no existe en la base de datos!.")));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.NOT_FOUND);
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

	@PostMapping("")
	public ResponseEntity<?> uploadPartitura(
			@RequestParam(name = "partituraPDF", required = true) MultipartFile partituraPDF,
			@RequestParam(name = "idObra", required = true) Long idObra,
			@RequestParam(name = "instrumento", required = true) String instrumento) {
		
		Map<String, Object> responseBody = new HashMap<>();
		
		Obra obra = obraService.findById(idObra);
		
		if(obra != null) {
			
		}

		
		Partitura partitura = new Partitura();
		obra.setId(idObra);

		try {
			partitura.setObra(obra);
			partitura.setInstrumento(instrumento);
			partitura.setPartituraPDFFromInputStream(partituraPDF.getInputStream());

			Partitura temp = partituraService.save(partitura);

			responseBody.put("mensaje", "Partitura guardada");
			responseBody.put("partitura", temp);

			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			responseBody.put("mensaje", "Error en la lectura del archivo PDF");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			responseBody.put("mensaje", "Error al realizar la consulta en la base de datos");
			responseBody.put("error", e.getMessage().concat(" : "));
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

	@GetMapping("/download/{idPartitura}")
	public ResponseEntity<?> findPartituraByIDURL(@PathVariable("idPartitura") Long idPartitura) {
		// Map<String, Object> responseBody = new HashMap<>();
		Partitura temp;

		try {

			temp = partituraService.findById(idPartitura);

			// responseBody.put("mensaje", "Partitura guardada");
			// responseBody.put("partitura", temp);
			

			return ResponseEntity
					.status(HttpStatus.OK).header("Content-Disposition", "attachment; filename= "
							+ temp.getObra().getNombre() + "_" + temp.getInstrumento() + ".pdf")
					.body(temp.getPartituraPDF());
			
			//return ResponseEntity.status(HttpStatus.OK)
				//.header("Content-Disposition",
					//		"inline; filename= " + temp.getObra().getNombre() + "_" + temp.getInstrumento() + ".pdf")
					//.body(temp.getPartituraPDF());

			//return ResponseEntity.ok()
					//.header("Content-Disposition", "attachment; filename=\"" + archivo.getInstrumento() + "\"")
					//.body(archivo.getPartituraPDF());
			// return new ResponseEntity<Map<String, Object>>(responseBody, null,
			// HttpStatus.OK);
		} catch (DataAccessException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

			// responseBody.put("mensaje", "Error al realizar la consulta en la base de
			// datos");
			// responseBody.put("error", e.getMessage().concat(" :
			// ").concat(e.getMostSpecificCause().getMessage()));
			// return new ResponseEntity<Map<String, Object>>(responseBody, null,
			// HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			// responseBody.put("mensaje", "Error al realizar la consulta en la base de
			// datos");
			// responseBody.put("error", e.getMessage().concat(" : "));
			// return new ResponseEntity<Map<String, Object>>(responseBody, null,
			// HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}

}
