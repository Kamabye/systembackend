package com.domain.system.controllers;

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

@RestController
@RequestMapping("apiv1/pdf")
@CrossOrigin(origins = "http://localhost:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.TRACE }, allowedHeaders = "Authorization")

public class PDFController {
	
	@Autowired
	private IPDFService pdfService;
	
	@PostMapping("/marca")
	public ResponseEntity<?> uploadPDF(@RequestParam(name = "archivoPDF", required = true) MultipartFile archivoPDF){
		byte[] archivoconmarca;
		archivoconmarca = pdfService.ponerMarcaAgua(archivoPDF);
		
		return ResponseEntity
				.status(HttpStatus.OK).header("Content-Disposition", "attachment; filename=archivo.pdf")
				.body(archivoconmarca);
	}
	
	

}
