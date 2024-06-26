package com.herokuapp.kamabye.models.postgresql;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.herokuapp.kamabye.models.Auditable;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	
	@Column(nullable = false)
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	
	@Column
	private Integer edad;
	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente", cascade = CascadeType.ALL)
// @JsonIgnore
	@JsonManagedReference
// @OrderBy("column ASC")
	private Set<Consulta> consultas = new HashSet<>();
	
	public void agregarConsulta(Consulta consulta) {
		if (this.consultas == null) {
			this.consultas = new HashSet<Consulta>();
		}
		this.consultas.add(consulta);
	}
	
	// @Transient
	public Integer getEdadCalculada() {
		if (this.fechaNacimiento != null) {
			long lapso = new Date().getTime() - fechaNacimiento.getTime();
			TimeUnit time = TimeUnit.DAYS;
			long lapsodif = time.convert(lapso, TimeUnit.MILLISECONDS);
			return (int) (lapsodif / 365);
		}
		return 0;
	}
	
	@PrePersist
	private void onCreate() {
		Date fecha = new Date();
		setCreatedAt(fecha);
		setModifiedAt(fecha);
		setFechaNacimiento(fecha);
	}
	
}
