package com.system.domain.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
			ClassPathResource resource = new ClassPathResource("files/marcaDeAgua.png");
			System.out.println(resource.getPath());
			
			return resource.getFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
