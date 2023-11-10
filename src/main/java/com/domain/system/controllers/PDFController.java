package com.domain.system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.domain.system.interfaces.IPDFService;
import com.domain.system.interfaces.IPartituraService;
import com.domain.system.models.postgresql.Obra;
import com.domain.system.models.postgresql.Partitura;

@RestController
@RequestMapping("apiv1/pdf")
@CrossOrigin(origins = "http://localhost:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")

public class PDFController {

	@Autowired
	private IPDFService pdfService;

	@Autowired
	private IPartituraService partituraService;

	@PostMapping("/marca")
	public ResponseEntity<?> uploadPDF(@RequestParam(name = "archivoPDF", required = true) MultipartFile archivoPDF,
			@RequestParam(name = "marcaDeAgua", required = true) MultipartFile marcaDeAgua) {
		Map<String, Object> responseBody = new HashMap<>();
		byte[] archivoconmarca;
		archivoconmarca = pdfService.ponerMarcaAgua(archivoPDF, marcaDeAgua);

		Partitura partitura = new Partitura();
		Obra obra = new Obra();
		obra.setId((long) 3);

		partitura.setInstrumento("marcadeAgua");
		partitura.setPartituraPDF(archivoconmarca);
		partitura.setObra(obra);

		Partitura temp = partituraService.save(partitura);

		responseBody.put("mensaje", "Partitura guardada");
		responseBody.put("partitura", temp);

		return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
	}

	@PostMapping("/unir")
	public ResponseEntity<?> unirPDF(
			@RequestParam(name = "archivosPDF", required = true) List<MultipartFile> archivosPDF) {
		Map<String, Object> responseBody = new HashMap<>();
		try {
			byte[] pdfUnido = pdfService.unirPDF(archivosPDF);

			//HttpHeaders headers = new HttpHeaders();
			//headers.setContentType(MediaType.APPLICATION_PDF);
			//headers.setContentDispositionFormData("attachment", "archivosUnidos.pdf");
			// return ResponseEntity.ok().headers(headers).body(pdfUnido);
			
			
			
			Obra obra = new Obra();
			obra.setId((long) 3);
			
			Partitura temp = new Partitura();
			
			temp.setInstrumento("partiturasunidad");
			temp.setPartituraPDF(pdfUnido);
			temp.setObra(obra);
			
			partituraService.save(temp);
			
			
			responseBody.put("mensaje", "Partitura guardada");
			responseBody.put("partitura", temp);

			return new ResponseEntity<Map<String, Object>>(responseBody, null, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error al unir los archivos PDF: " + e.getMessage());
		}
	}

}
