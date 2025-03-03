package com.system.domain.model.postgresql;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "Pacientes", indexes = {
  @Index(name = "idx_id_paciente", columnList = "idPaciente")
})
@Data
@EqualsAndHashCode(exclude = "consultas", callSuper = true)
@ToString(exclude = "consultas")
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
	private LocalDate fechaNacimiento;
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
	
	private OffsetDateTime fechaCreacion;
	
	private OffsetDateTime fechaModificacion;
	
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
	
	@Transient
	public Integer getEdadCalculada() {
		LocalDate fechaActual = LocalDate.now();
		if (this.fechaNacimiento != null) {
			Period periodo = Period.between(this.fechaNacimiento, fechaActual);
			int anios = periodo.getYears();
			return anios;
		}
		return this.edad;
		
	}
	
	@PrePersist
	private void onCreate() {
		OffsetDateTime fechaActual = OffsetDateTime.now();
		setFechaCreacion(fechaActual);
		setFechaModificacion(fechaActual);
		
		if (this.edad != null && this.edad > 0) {
			setFechaNacimiento(LocalDate.now().minusYears(this.edad));
		}
	}
	
	@PreUpdate
	private void onUpdate() {
		setFechaModificacion(OffsetDateTime.now()); // Se actualiza updatedAt cuando la entidad se actualiza
	}
	
}