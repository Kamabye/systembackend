package com.system.domain.models.postgresql;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.system.domain.models.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Partituras")
@Data
@EqualsAndHashCode(callSuper = true)
//@ToString(exclude = { "partituraPDF", "vistaPreviaPDF", "vistaPreviaPDF", "xml" })
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Genera un cosntructor para cada parámetro finales o no nulos
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) // Ignora propiedades del Proxy Hibernate durante la serializacion
@JsonIdentityInfo(scope = Partitura.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPartitura")
public class Partitura extends Auditable implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	@Column(updatable = false, nullable = false)
	private Long idPartitura;
	
	private String instrumento;
	
	@OneToMany(mappedBy = "partitura", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	// @JsonManagedReference
	@JsonIgnore
	private Set<PartituraBlob> partiturasBlob;
	
	@ManyToOne
	@JoinColumn(name = "idObra")
	// @JsonBackReference
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	// @JsonIgnore
	private Obra obra;
	
	private static final long serialVersionUID = 1L;
	
}
