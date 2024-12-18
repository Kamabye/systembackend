package com.system.domain.models.postgresql;

import java.io.Serializable;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.system.domain.models.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Consultas")
@Data
@EqualsAndHashCode(exclude = "paciente", callSuper = true)
@ToString(exclude = "paciente")
//@Builder
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Genera un cosntructor para cada parámetro finales o no nulos
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIgnoreProperties({ "imagen" })
@JsonIdentityInfo(scope = Consulta.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idConsulta")
public class Consulta extends Auditable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	// Column(name = "idUsuario", updatable = false, nullable = false)
	@Column(updatable = false, nullable = false)
	private Long idConsulta;
	
	/**
	 * Medidas de Autorefractometro
	 */
	// Esferas
	@Column
	@NumberFormat(pattern = "#.##")
	private Double totalDer;
	@Column
	@NumberFormat(pattern = "#.##")
	private Double totalIzq;
	// Cilindros
	@Column
	@NumberFormat(pattern = "#.##")
	private Double totalAstigDer;
	@Column
	@NumberFormat(pattern = "#.##")
	private Double totalAstigIzq;
	// Ejes
	@Column
	private Integer totalAngDer;
	@Column
	private Integer totalAngIzq;
	
	/**
	 * Medidas caja de pruebas
	 */
	
	// Esfera
	@Column
	@NumberFormat(pattern = "#.##")
	private Double subLejDer;
	@Column
	@NumberFormat(pattern = "#.##")
	private Double subLejIzq;
	
	// Cilindro
	@Column
	@NumberFormat(pattern = "#.##")
	private Double subLejAstigDer;
	@Column
	@NumberFormat(pattern = "#.##")
	private Double subLejAstigIzq;
	@Column
	// Eje
	private Integer subLejAngDer;
	@Column
	private Integer subLejAngIzq;
	
	/**
	 * Adicional
	 */
	@Column
	@NumberFormat(pattern = "#.##")
	private Double addDer;
	@Column
	@NumberFormat(pattern = "#.##")
	private Double addIzq;
	
	// Agudeza Visual
	@Column
	private Integer AVDer;
	@Column
	private Integer AVIzq;
	
	//Distancia Inter Pupilar
	@Column
	private Integer dip;
	
	// Comentarios
	@Column
	private String rx;
	
	@Column
	private String ocupacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	// @JsonIgnore
	//@JsonBackReference
	@JoinColumn(name = "idPaciente")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Paciente paciente;
	
	//@Transient
	public String getAVDerSnellen() {
		String snellen = "";
		switch (AVDer) {
			case 1:
				snellen = "20/200";
				break;
			case 2:
				snellen = "20/100";
				break;
			case 3:
				snellen = "20/70";
				break;
			case 4:
				snellen = "20/50";
				break;
			case 5:
				snellen = "20/40";
				break;
			case 6:
				snellen = "20/30";
				break;
			case 7:
				snellen = "20/25";
				break;
			case 8:
				snellen = "20/20";
				break;
			case 9:
				snellen = "20/15";
				break;
			case 10:
				snellen = "20/12";
				break;
			case 11:
				snellen = "20/10";
				break;
		}
		return snellen;
	}
	
	//@Transient
	public String getAVIzqSnellen() {
		String snellen = "";
		switch (AVIzq) {
			case 1:
				snellen = "20/200";
				break;
			case 2:
				snellen = "20/100";
				break;
			case 3:
				snellen = "20/70";
				break;
			case 4:
				snellen = "20/50";
				break;
			case 5:
				snellen = "20/40";
				break;
			case 6:
				snellen = "20/30";
				break;
			case 7:
				snellen = "20/25";
				break;
			case 8:
				snellen = "20/20";
				break;
			case 9:
				snellen = "20/15";
				break;
			case 10:
				snellen = "20/12";
				break;
			case 11:
				snellen = "20/10";
				break;
		}
		return snellen;
	}
	
}
