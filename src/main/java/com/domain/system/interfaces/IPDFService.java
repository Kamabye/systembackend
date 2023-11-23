package com.domain.system.interfaces;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IPDFService {
	
	byte[] ponerMarcaAgua(MultipartFile archivoPDF, MultipartFile marcaDeAgua);
	
	byte[] ponerMarcaAgua(MultipartFile archivoPDF);
	
	byte[] ponerMarcaAgua(InputStream inputStream);
	
	byte[] unirPDF(List<MultipartFile> archivosPDF);

}
