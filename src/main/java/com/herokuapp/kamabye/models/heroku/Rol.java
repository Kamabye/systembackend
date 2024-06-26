package com.herokuapp.kamabye.models.heroku;

import java.io.Serializable;

import com.herokuapp.kamabye.models.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Roles")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rol extends Auditable implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	@Column(name = "idRol", updatable = false, nullable = false)
	protected Long id;

	@Column(unique = true)
	private String rol;

	/*
	 * //@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade =
	 * CascadeType.ALL)
	 * 
	 * @ManyToMany(mappedBy = "roles") private Set<Usuario> usuarios;
	 * 
	 * public void addUser(Usuario usuario) { if (usuarios == null) { usuarios = new
	 * HashSet<Usuario>(); } usuarios.add(usuario); //usuario.getRoles().add(this);
	 * }
	 */

	private static final long serialVersionUID = 1L;

}
