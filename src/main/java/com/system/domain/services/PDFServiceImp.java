package com.system.domain.services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdfwriter.compress.CompressParameters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.system.domain.interfaces.IPDFService;
import com.system.domain.models.dto.LobDTO;

@Service
@Primary
public class PDFServiceImp implements IPDFService {
	
	private static final int BUFFER_MAX_MULTIPARTFILE = 8192;
	
	private static final int PDF_SIZE_MAX = 2 * 1024 * 1024;// 2MB para archivos pdf
	private static final int AUDIO_SIZE_MAX = 10 * 1024 * 1024;// 2MB para archivos audio
	
	private static final List<String> EXTENSIONES_PDF_PERMITIDAS = Arrays.asList("pdf", "PDF");
	
	@Autowired
	private ResourcesService resourceService;
	
	@Override
	public boolean isPDFValid(MultipartFile partituraPDF) {
		
		if (isSizeValidPDF(partituraPDF) && isPDF(partituraPDF)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isSizeValidPDF(MultipartFile multipartFile) {
		
		if (multipartFile != null && !multipartFile.isEmpty() && multipartFile.getSize() <= PDF_SIZE_MAX) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isPDF(MultipartFile partituraPDF) {
		
		return esExtensionPDFPermitida(partituraPDF);
	}
	
	@Override
	public boolean esExtensionPDFPermitida(MultipartFile partituraPDF) {
		
		String nombreArchivo = partituraPDF.getOriginalFilename();
		
		String extensionArchivo = obtenerExtension(nombreArchivo);
		
		return EXTENSIONES_PDF_PERMITIDAS.contains(extensionArchivo);
		
	}
	
	@Override
	public String obtenerExtension(String nombreArchivo) {
		if (nombreArchivo != null && nombreArchivo.lastIndexOf(".") != -1) {
			return nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
		}
		return "";
	}
	
	@Override
	public byte[] ponerMarcaAgua(MultipartFile archivoPDF) {
		try (InputStream inputStream = archivoPDF.getInputStream()) {
			// byte[] buffer = new byte[BUFFER_MAX_MULTIPARTFILE];
			// int bytesRead;
			// System.out.println("Poner marca de agua multipart");
			// byte[] bytes = ByteStreams.toByteArray(inputStream);
			
			// while ((bytesRead = inputStream.read(buffer)) != -1) {
			// Realizar operaciones con el bloque de bytes leído
			// Por ejemplo, puedes almacenarlos en una base de datos o realizar algún
			// procesamiento específico.
			// Aquí puedes procesar el bloque de bytes según tus necesidades.
			// }
			
			// while ((bytesRead = ByteStreams.read(inputStream, buffer, 0, buffer.length))
			// != -1) {
			// Realizar operaciones con el bloque de bytes leído
			// Por ejemplo, puedes almacenarlos en una base de datos o realizar algún
			// procesamiento específico.
			// Aquí puedes procesar el bloque de bytes según tus necesidades.
			// }
			
			byte[] bytes = ponerMarcaAgua(inputStream);
			
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public byte[] ponerMarcaAgua(InputStream inputStream) {
		try (PDDocument originalPDF = Loader.loadPDF(new RandomAccessReadBuffer(inputStream))) {
			
			PDImageXObject image = PDImageXObject.createFromFileByContent(resourceService.getMarcaDeAguaFile(),originalPDF);
			System.out.println("PDF y Marca de agua cargados");
			int numPage = 1;
			for (PDPage page : originalPDF.getPages()) {
				
				System.out.println("Pagina cargada " + numPage);
				
				try (PDPageContentStream contentStream = new PDPageContentStream(originalPDF, page,
				  PDPageContentStream.AppendMode.APPEND, true, true)) {
					
					PDRectangle pageSize = page.getMediaBox();
					
					float anchoPagina = pageSize.getWidth();
					float altoPagina = pageSize.getHeight();
					
					float anchoImagen = image.getWidth();
					float altoImagen = image.getHeight();
					
					// Cuando la imagen es mayor que el PDF se sale del area
					float centerX = (pageSize.getWidth() - anchoImagen) / 2;
					float centerY = (pageSize.getHeight() - altoImagen) / 2;
					
					// contentStream.drawImage(image, centerX, centerY, imageWidth, imageHeight);
					// contentStream.drawImage(image, 0, 0, imageWidth, imageHeight);
					contentStream.drawImage(image, 0, 0, anchoPagina, altoPagina);
					
					System.out.println("Pagina sellada " + numPage);
					numPage++;
				}
			}
			
			try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
				originalPDF.save(byteArrayOutputStream);
				System.out.println("Archivo sellado");
				return byteArrayOutputStream.toByteArray();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public byte[] unirPDF(List<LobDTO> partituras) {
		try (PDDocument resultado = new PDDocument()) {
			PDFMergerUtility merger = new PDFMergerUtility();
			int numPage = 1;
			for (LobDTO partitura : partituras) {
				merger.appendDocument(resultado, Loader
				  .loadPDF(new RandomAccessReadBuffer(new ByteArrayInputStream(partitura.getPartituraPDF()))));
				System.out.println("Se agregó el instrumento " + numPage);
				numPage++;
			}
			
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				System.out.println("Se va a guardar el archivo");
				resultado.save(outputStream, CompressParameters.NO_COMPRESSION);
				System.out.println("Se archivo completado");
				return outputStream.toByteArray();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public byte[] pdfToPng(MultipartFile pdfFile) throws IOException {
		try (PDDocument pdfFileDocumento = Loader.loadPDF(new RandomAccessReadBuffer(pdfFile.getInputStream()))) {
			PDFRenderer pdfRenderer = new PDFRenderer(pdfFileDocumento);
			int numberOfPages = pdfFileDocumento.getNumberOfPages();

// Obtener las dimensiones de cada página
   BufferedImage firstPage = pdfRenderer.renderImage(0);
   int width = firstPage.getWidth();
   int height = firstPage.getHeight();

   // Crear una imagen resultante con el tamaño total
   BufferedImage result = new BufferedImage(width, height * numberOfPages, BufferedImage.TYPE_INT_RGB);
   Graphics2D g2d = result.createGraphics();

   for (int i = 0; i < numberOfPages; i++) {
       BufferedImage pageImage = pdfRenderer.renderImage(i);
       g2d.drawImage(pageImage, 0, i * height, null);
   }

   g2d.dispose();

   // Convertir la imagen a bytes PNG
   ByteArrayOutputStream baos = new ByteArrayOutputStream();
   ImageIO.write(result, "png", baos);
   return baos.toByteArray();
			
		}

	}
	
}
