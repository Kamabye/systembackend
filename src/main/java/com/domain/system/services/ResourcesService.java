package com.domain.system.services;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
public class ResourcesService {
	
	
	public BufferedImage marcaDeAgua() {
		
		try {
			Resource resource = new ClassPathResource("marcaDeAgua.png");

            InputStream inputStream = resource.getInputStream();
            
            BufferedImage marcadeagua = javax.imageio.ImageIO.read(inputStream);

            return marcadeagua;
		}
		catch(Exception e) {
			return null;
		}
	}

}
