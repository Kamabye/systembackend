package com.domain.system.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.domain.system.interfaces.IPDFService;

@Service
public class PDFServiceImp implements IPDFService {

	@Override
	public byte[] ponerMarcaAgua(MultipartFile archivPDF) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] unirPDF(List<MultipartFile> archivosPDF) {
		// TODO Auto-generated method stub
		return null;
	}

}
