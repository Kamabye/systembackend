package com.system.domain.model.postgresql;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.system.domain.model.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "Usuarios", indexes = {
  @Index(name = "idx_id_usuario", columnList = "idUsuario")
})
@Data
@EqualsAndHashCode(exclude = "roles", callSuper = false)
@ToString(exclude = { "roles" })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) // Ignora propiedades del Proxy Hibernate durante la serializacion
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
	
	@Column
	private String primerApellido;
	@Column
	private String segundoApellido;
	@Column
	private Integer dia;
	@Column
	private Integer mes;
	@Column
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
	
	@Column(nullable = false)
	private Boolean estatus;
	
	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Boolean estatusBloqueo;
	
	// @Temporal(TemporalType.DATE)
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column
	private LocalDate dateOfBirth;
	
	/**
	 * Persist: Cuando se persiste un Usuario, también se persistirán los Rol
	 * asociados. Merge: Cuando se actualiza un Usuario, también se actualizarán los
	 * Rol asociados. Remove: Cuando se elimina un Usuario, también se eliminarán
	 * los Rol asociados. Refresh: Cuando se refresca un Usuario, también se
	 * refrescarán los Rol asociados. Detach: Cuando se desasocia un Usuario,
	 * también se desasociarán los Rol asociados. El atributo orphanRemoval = true
	 * asegura que cuando un Rol se elimina del conjunto de roles del Usuario,
	 * también se eliminará de la base de datos.
	 */
	// @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
	// CascadeType.MERGE, orphanRemoval = true })
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UsuariosRoles", joinColumns = @JoinColumn(name = "idUsuario", nullable = false, referencedColumnName = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idRol", nullable = false, referencedColumnName = "idRol"), uniqueConstraints = {
	  @UniqueConstraint(columnNames = { "idUsuario", "idRol" }) }, indexes = {
	    @Index(name = "idx_usuario_rol_usuario_rol", columnList = "idUsuario, idRol"),
	    @Index(name = "idx_usuario_rol_usuario", columnList = "idUsuario"),
	    @Index(name = "idx_usuario_rol_rol", columnList = "idRol")
	  })
	
	// @EqualsAndHashCode.Exclude
	// @ToString.Exclude
	// @JsonManagedReference
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	// @JsonIgnoreProperties("usuarios")
	// @JsonIgnore
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
	}
}
