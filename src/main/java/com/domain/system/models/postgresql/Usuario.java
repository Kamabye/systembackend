package com.domain.system.models.postgresql;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.domain.system.models.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuarios")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Genera un cosntructor para cada parámetro finales o no nulos
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario extends Auditable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	//@Column(name = "idUsuario", updatable = false, nullable = false)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(unique = true, nullable = true)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	//@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Column(nullable = false)
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Integer dia;
	private Integer mes;
	private Integer anio;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	//@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private byte[] imagen;

	@Column(nullable = false)
	private Boolean estatus;

	@Column(nullable = false)
	private Boolean estatusBloqueo;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@ManyToMany(fetch = FetchType.EAGER)
	// @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
	// CascadeType.MERGE })
	@JoinTable(name = "UsuariosRoles", // Nombre de la tabla que se creará
			joinColumns = @JoinColumn(name = "idUsuario", nullable = false, referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "idRol", nullable = false, referencedColumnName = "id"),
			uniqueConstraints = {
					@UniqueConstraint(columnNames = { "idUsuario", "idRol" }) })
	@JsonManagedReference //Cuando tienes relaciones bidireccionales complejas y necesitas mantener la integridad de las referencias en ambas direcciones.
	//@JsonIgnore
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Rol> roles;
	// private List<Rol> roles;

	public void addRole(Rol rol) {
		if (roles == null) {
			roles = new HashSet<Rol>();
		}
		roles.add(rol);
		// rol.getUsuarios().add(this);
	}

	public boolean hasRole(String roleName) {
		Iterator<Rol> iterator = roles.iterator();

		while (iterator.hasNext()) {
			Rol rol = iterator.next();
			if (rol.getRol().equals(roleName)) {
				return true;
			}
		}
		return false;
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
