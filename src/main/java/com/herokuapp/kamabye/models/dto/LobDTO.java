package com.herokuapp.kamabye.models.dto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor // Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Crea un constructor que solicita todos los parámetros de la clase que no son
					// FINAL
public class LobDTO {
	
	@Lob
	private byte[] partituraPDF;

}
