package com.herokuapp.kamabye.models.postgresql;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.herokuapp.kamabye.models.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Roles")
@Data
@EqualsAndHashCode(exclude = "usuarios", callSuper = true)
@ToString(exclude = "usuarios")
//@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(exclude = "roles")
//@ToString(exclude = "roles")
@JsonIdentityInfo(scope = Rol.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRol")
//@JsonIgnoreProperties("usuarios")
public class Rol extends Auditable implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	// @Column(name = "idRol", updatable = false, nullable = false)
	@Column(updatable = false, nullable = false)
	private Long idRol;

	@Column(unique = true)
	private String rol;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "UsuariosRoles", joinColumns = @JoinColumn(name = "idRol", nullable = false, referencedColumnName = "idRol"), inverseJoinColumns = @JoinColumn(name = "idUsuario", nullable = false, referencedColumnName = "idUsuario"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "idUsuario", "idRol" }) })
	// @ManyToMany(mappedBy = "roles")
	// @EqualsAndHashCode.Exclude
	// @ToString.Exclude
	//@JsonBackReference
	@JsonIgnore
	//@JsonIgnoreProperties("roles")
	private Set<Usuario> usuarios = new HashSet<>();

	private static final long serialVersionUID = 1L;

}
