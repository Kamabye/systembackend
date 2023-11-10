package com.domain.system.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.domain.system.interfaces.IPDFService;

@Service
public class PDFServiceImp implements IPDFService {
	
	@Autowired
	private ResourcesService resourceService;

	@Override
	public byte[] ponerMarcaAgua(MultipartFile archivoPDF, MultipartFile marcaDeAgua) {

		try (InputStream pdfInputStream = archivoPDF.getInputStream();
				InputStream marcaDeAguaInputStream = marcaDeAgua.getInputStream()) {
			// PDDocument documentoPDF = Loader.loadPDF(pdfInputStream.readAllBytes());
			PDDocument documentoPDF = Loader.loadPDF(new RandomAccessReadBuffer(pdfInputStream));
			// PDFRenderer pdfRenderer = new PDFRenderer(documentoPDF);

			BufferedImage imagenMarcaDeAgua = javax.imageio.ImageIO.read(marcaDeAguaInputStream);

			for (int i = 0; i < documentoPDF.getNumberOfPages(); i++) {
				PDPage page = documentoPDF.getPage(i);
				PDRectangle pageSize = page.getMediaBox();
				float x = (pageSize.getWidth() - imagenMarcaDeAgua.getWidth()) / 2;
				float y = (pageSize.getHeight() - imagenMarcaDeAgua.getHeight()) / 2;

				PDPageContentStream contentStream = new PDPageContentStream(documentoPDF, page,
						PDPageContentStream.AppendMode.APPEND, true);
				contentStream
						.drawImage(
								PDImageXObject.createFromByteArray(documentoPDF,
										convertirImagenABytes(imagenMarcaDeAgua), "String"),
								x, y, imagenMarcaDeAgua.getWidth(), imagenMarcaDeAgua.getHeight());
				contentStream.close();
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			documentoPDF.save(outputStream);
			documentoPDF.close();

			return outputStream.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	private byte[] convertirImagenABytes(BufferedImage imagen) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		javax.imageio.ImageIO.write(imagen, "png", baos);
		return baos.toByteArray();
	}

	@Override
	public byte[] unirPDF(List<MultipartFile> archivosPDF) {

		try (PDDocument resultado = new PDDocument()) {
			PDFMergerUtility merger = new PDFMergerUtility();

			for (MultipartFile archivoPDF : archivosPDF) {
				InputStream pdfInputStream = archivoPDF.getInputStream();
				merger.appendDocument(resultado, Loader.loadPDF(new RandomAccessReadBuffer(pdfInputStream)));
			}
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			resultado.save(outputStream);
			return outputStream.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public byte[] ponerMarcaAgua(MultipartFile archivoPDF) {
		try (InputStream pdfInputStream = archivoPDF.getInputStream()) {
			// PDDocument documentoPDF = Loader.loadPDF(pdfInputStream.readAllBytes());
			PDDocument documentoPDF = Loader.loadPDF(new RandomAccessReadBuffer(pdfInputStream));
			// PDFRenderer pdfRenderer = new PDFRenderer(documentoPDF);

			BufferedImage imagenMarcaDeAgua = resourceService.marcaDeAgua();

			for (int i = 0; i < documentoPDF.getNumberOfPages(); i++) {
				PDPage page = documentoPDF.getPage(i);
				PDRectangle pageSize = page.getMediaBox();
				float x = (pageSize.getWidth() - imagenMarcaDeAgua.getWidth()) / 2;
				float y = (pageSize.getHeight() - imagenMarcaDeAgua.getHeight()) / 2;

				PDPageContentStream contentStream = new PDPageContentStream(documentoPDF, page,
						PDPageContentStream.AppendMode.APPEND, true);
				contentStream
						.drawImage(
								PDImageXObject.createFromByteArray(documentoPDF,
										convertirImagenABytes(imagenMarcaDeAgua), "String"),
								x, y, imagenMarcaDeAgua.getWidth(), imagenMarcaDeAgua.getHeight());
				contentStream.close();
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			documentoPDF.save(outputStream);
			documentoPDF.close();

			return outputStream.toByteArray();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
