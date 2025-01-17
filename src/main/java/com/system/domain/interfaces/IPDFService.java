package com.system.domain.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.system.domain.models.dto.LobDTO;

public interface IPDFService {

	boolean isPDFValid(MultipartFile partituraPDF);

	boolean isSizeValidPDF(MultipartFile multipartFile);
	
	String obtenerExtension(String nombreArchivo);

	boolean esExtensionPDFPermitida(MultipartFile partituraPDF);

	boolean isPDF(MultipartFile partituraPDF);

	byte[] ponerMarcaAgua(MultipartFile archivoPDF);

	byte[] ponerMarcaAgua(InputStream inputStream);

	byte[] unirPDF(List<LobDTO> partituras);

	byte[] pdfToPng(MultipartFile pdfFile) throws IOException;
	

}
