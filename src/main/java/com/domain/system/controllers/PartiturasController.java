package com.domain.system.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

	}

	@PostMapping("")
	public ResponseEntity<?> uploadPartitura(
			@RequestParam(name = "partitura", required = true) MultipartFile partituraPDF,
			@RequestParam(name = "idObra", required = true) Long idObra,
			@RequestParam(name = "instrumento", required = true) String instrumento) {
		
		Map<String, Object> responseBody = new HashMap<>();
		Partitura partitura = new Partitura();
		Obra obra = new Obra();
		obra.setId(idObra);
		
		
		try {
			partitura.setObra(obra);
			partitura.setInstrumento(instrumento);
			partitura.setPartituraPDFFromInputStream(partituraPDF.getInputStream());
			
			Partitura temp = partituraService.save(partitura);
			
			responseBody.put("mensaje", "Partitura guardada");
			responseBody.put("partitura", temp);
			
			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}
}
