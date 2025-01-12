package com.system.domain.models.postgresql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.system.domain.models.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Pacientes")
@Data
@EqualsAndHashCode(exclude = "consultas", callSuper = true)
@ToString(exclude = "consultas")
//@Builder
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Genera un cosntructor para cada parámetro finales o no nulos
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIgnoreProperties({ "imagen" })
@JsonIdentityInfo(scope = Paciente.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPaciente")
public class Paciente extends Auditable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	// Column(name = "idUsuario", updatable = false, nullable = false)
	@Column(updatable = false, nullable = false)
	private Long idPaciente;
	
	// @Column(nullable = false)
	@Column
	private String nombres;
	@Column
	private String primerApellido;
	@Column
	private String segundoApellido;
	
	@Column
	private Integer edad;
	@Column
	// @Temporal(TemporalType.DATE)
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private OffsetDateTime fechaNacimiento;
	@Column
	private String sexo;
	@Column
	private String celular;
	@Column
	private String telefono;
	@Column
	private String email;
	@Column
	private String direccion;
	
//En esta relación se debe mapear la clase que será el inverso
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente", orphanRemoval =
	// true)
// @JsonIgnore
	// @JsonManagedReference
// @OrderBy("column ASC")
	private Set<Consulta> consultas = new HashSet<>();
	
	public void agregarConsulta(Consulta consulta) {
		if (this.consultas == null) {
			this.consultas = new HashSet<Consulta>();
		}
		this.consultas.add(consulta);
		consulta.setPaciente(this);
	}
	
	public boolean hasConsulta(Long idConsulta) {
		
		return this.getConsultas().stream().anyMatch(consulta -> consulta.getIdConsulta().equals(idConsulta));
		
	}
	
	// @Transient
	public Integer getEdadCalculada() {
		OffsetDateTime fechaActual = OffsetDateTime.now();
		if (fechaNacimiento != null) {
			return (int) fechaNacimiento.until(fechaActual, ChronoUnit.YEARS);
		}
		return 0;
		
	}
	
	public OffsetDateTime restarAnios(Integer anios) {
		return OffsetDateTime.now().minusYears(anios);
	}
	
	@PrePersist
	private void onCreate() {
		LocalDateTime fecha = LocalDateTime.now();
		setCreatedAt(fecha);
		setModifiedAt(fecha);
		
		if (this.edad != null && this.edad > 0) {
			setFechaNacimiento(this.restarAnios(edad));
		}
	}
	
}
