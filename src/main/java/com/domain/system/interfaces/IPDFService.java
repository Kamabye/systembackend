package com.domain.system.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IPDFService {
	
	byte[] ponerMarcaAgua(MultipartFile archivPDF, MultipartFile marcaDeAgua);
	
	byte[] unirPDF(List<MultipartFile> archivosPDF);

}
