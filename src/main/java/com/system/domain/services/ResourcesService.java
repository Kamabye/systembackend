package com.system.domain.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourcesService {
	
	public BufferedImage getMarcaDeAguaBufferedImage() {
		
		try {
			Resource resource = new ClassPathResource("files/marcaDeAgua.png");
			
			InputStream inputStream = resource.getInputStream();
			
			BufferedImage marcadeagua = javax.imageio.ImageIO.read(inputStream);
			
			return marcadeagua;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public File getMarcaDeAguaFile() {
		
		try {
			
			Class<?> claseActual = ResourcesService.class;
// Obtener la ubicación del archivo de la clase como un recurso
			URL url = claseActual.getResource("/" + claseActual.getName().replace('.', '/') + ".class");
			
			ClassPathResource resource = new ClassPathResource("files/marcaDeAgua.png");
			
			if (url != null) {
				
//	 Convertir la URL a un objeto File
				File archivo = new File(url.toURI());
				
				// Obtener la ruta absoluta del archivo
				String rutaAbsoluta = archivo.getAbsolutePath();
				
				// Imprimir la ruta absoluta en la consola
				System.out.println("Ruta absoluta del archivo de la clase: " + rutaAbsoluta);
				
				return resource.getFile();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public InputStream getMarcaDeAguaInputStream() {
		
		try {
			
			ClassPathResource resource = new ClassPathResource("files/marcaDeAgua.png");
			
			return resource.getInputStream();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
