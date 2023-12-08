package com.domain.system.interfaces;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.domain.system.models.dto.LobDTO;
import com.domain.system.models.postgresql.Obra;

public interface IPDFService {

	boolean isPDFValid(MultipartFile partituraPDF);

	boolean isSizeValidPDF(MultipartFile multipartFile);
	
	String obtenerExtension(String nombreArchivo);

	boolean esExtensionPDFPermitida(MultipartFile partituraPDF);

	boolean isPDF(MultipartFile partituraPDF);

	byte[] ponerMarcaAgua(MultipartFile archivoPDF);

	byte[] ponerMarcaAgua(InputStream inputStream);

	byte[] unirPDF(List<LobDTO> partituras);

	

}