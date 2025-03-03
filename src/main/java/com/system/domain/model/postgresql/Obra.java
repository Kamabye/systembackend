package com.system.domain.model.postgresql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.system.domain.model.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "Obras", indexes = {
  @Index(name = "idx_id_obra", columnList = "idObra")
})
@Data
@EqualsAndHashCode(callSuper = false) // Esta anotación se usa cuando la clase extiende de otra y quieres omitir los
                                      // atributos de la clase padre en el equal y hashcode
@ToString(exclude = "partituras")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(scope = Obra.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObra")
public class Obra extends Auditable implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	@Column(updatable = false, nullable = false)
	private Long idObra;
	
	@Column
	private String nombre;
	
	@Column
	private String compositor;
	
	@Column
	private String arreglista;
	
	@Column
	private String letrista;
	
	@Column
	private String genero;
	
	@Column(precision = 10, scale = 3)
	private BigDecimal precio;
	
	@DecimalMin("0.00")
	@DecimalMax("1.00")
	@Column(precision = 4, scale = 2)
	private BigDecimal iva;
	
	@Column
	private String embedAudio;
	
	@Column
	private String embedVideo;
	
	// @Lob
	// @Basic(fetch = FetchType.LAZY)
	// @JsonIgnore
	// @Column
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	// private byte[] audio;
	
	@OneToMany(mappedBy = "obra", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	// @JsonManagedReference
	@JsonIgnore
	private Set<Partitura> partituras;
	
	@Column
	private Long idUsuario;
	
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
		return this.getPartituras().stream().anyMatch(consulta -> consulta.getInstrumento().equals(instrumento));
		
		/*
		 * Iterator<Partitura> iterator = partituras.iterator();
		 * 
		 * while (iterator.hasNext()) { Partitura partitura = iterator.next(); if
		 * (partitura.getInstrumento().equals(instrumento)) { return true; } } return
		 * false;
		 */
	}
	
	@PrePersist
	private void onCreate() {
		this.setIva(BigDecimal.valueOf(0.16));
		
	}
	
	private static final long serialVersionUID = 1L;
	
}
