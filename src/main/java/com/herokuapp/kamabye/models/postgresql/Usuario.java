package com.herokuapp.kamabye.models.postgresql;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.herokuapp.kamabye.models.Auditable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Usuarios")
@Data
@EqualsAndHashCode(exclude = "roles", callSuper = true)
@ToString(exclude = "roles")
//@Builder
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Genera un cosntructor para cada parámetro finales o no nulos
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIgnoreProperties({ "imagen" })
@JsonIdentityInfo(scope = Usuario.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idUsuario")
public class Usuario extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	// Column(name = "idUsuario", updatable = false, nullable = false)
	@Column(updatable = false, nullable = false)
	private Long idUsuario;

	@Column(unique = true, nullable = true)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	// @JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Column(nullable = false)
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Integer dia;
	private Integer mes;
	private Integer anio;

	// @Basic(fetch = FetchType.EAGER)

	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	// @JsonSerialize(using = LobSerializer.class)
	// @Getter(AccessLevel.NONE)
	// @JsonIgnore
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	// @Lob
	// private byte[] imagen;

	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	// @Column(columnDefinition = "BYTEA")
	// private byte[] imagenBytea;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column
	private Blob imagen;

	@Column(nullable = false)
	private Boolean estatus;

	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Boolean estatusBloqueo;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column
	private Date dateOfBirth;

	// @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
	// CascadeType.MERGE })
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UsuariosRoles", joinColumns = @JoinColumn(name = "idUsuario", nullable = false, referencedColumnName = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idRol", nullable = false, referencedColumnName = "idRol"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "idUsuario", "idRol" }) })

	// @EqualsAndHashCode.Exclude
	// @ToString.Exclude
	// @JsonManagedReference
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	// @JsonIgnoreProperties("usuarios")
	private Set<Rol> roles = new HashSet<>();

	public void addRole(Rol rol) {

		if (this.roles == null) {
			this.roles = new HashSet<Rol>();
		}

		this.roles.add(rol);
		rol.getUsuarios().add(this);
	}

	public boolean hasRole(String roleName) {

		return this.roles.stream().anyMatch(rol -> rol.getRol().equals(roleName));

		/*
		 * Iterator<Rol> iterator = roles.iterator();
		 * 
		 * while (iterator.hasNext()) { Rol rol = iterator.next(); if
		 * (rol.getRol().equals(roleName)) { return true; } } return false;
		 */

	}

	public void removeRol(Rol rol) {
		this.roles.remove(rol);
		rol.getUsuarios().remove(this);
	}

	@PrePersist
	private void onCreate() {
		estatus = false;
		estatusBloqueo = false;
		Date fecha = new Date();
		setCreatedAt(fecha);
		setModifiedAt(fecha);
	}
}
