package com.domain.system.models.postgresql;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.domain.system.models.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.io.ByteStreams;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Obras")
@Data
@EqualsAndHashCode(callSuper = true) // Esta anotación se usa cuando la clase extiende de otra
@Builder
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Crea un constructor que solicita todos los parámetros de la clase que no son
					// FINAL
public class Obra extends Auditable implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	@Column(name = "idObra", updatable = false, nullable = false)
	private Long id;

	private String nombre;

	private String compositor;

	private String arreglista;

	private String letrista;

	private BigDecimal precio;

	private String genero;
	
	private String embedAudio;

	private String embedVideo;
	
	@Lob
	//@Basic(fetch = FetchType.LAZY)
	private byte[] audio;

	@OneToMany(mappedBy = "obra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<Partitura> partituras;

	public void addPartitura(Partitura partitura) {
		if (partituras == null) {
			partituras = new HashSet<Partitura>();
		}

		/*
		 * if (partituras.stream().noneMatch(existingPartitura ->
		 * existingPartitura.equals(partitura))) { partituras.add(partitura);
		 * partitura.setObra(this); }
		 */

		partituras.add(partitura);
		partitura.setObra(this);
	}

	public boolean hasPartitura(String instrumento) {
		Iterator<Partitura> iterator = partituras.iterator();

		while (iterator.hasNext()) {
			Partitura partitura = iterator.next();
			if (partitura.getInstrumento().equals(instrumento)) {
				return true;
			}
		}
		return false;
	}

	public void setAudioFromInputStream(InputStream inputStream) throws IOException {
		this.audio = ByteStreams.toByteArray(inputStream);
	}

	private static final long serialVersionUID = 1L;

}
