package com.domain.system.interfaces;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IMultipartFileService {

	boolean isPDFValid(MultipartFile partituraPDF);

	boolean isSizeValidPDF(MultipartFile multipartFile);
	
	String obtenerExtension(String nombreArchivo);

	boolean esExtensionPDFPermitida(MultipartFile partituraPDF);

	boolean isPDF(MultipartFile partituraPDF);

	byte[] ponerMarcaAgua(MultipartFile archivoPDF);

	byte[] ponerMarcaAgua(InputStream inputStream);

	byte[] unirPDF(List<MultipartFile> archivosPDF);

	

}
