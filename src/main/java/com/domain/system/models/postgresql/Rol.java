package com.domain.system.models.postgresql;

import java.io.Serializable;
import java.util.Set;

import com.domain.system.models.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	private Long id;

	@Column(unique = true)
	private String rol;
	
	
	@ManyToMany(mappedBy = "roles")
	//@JsonIgnore //Si solo necesitas romper un ciclo de referencia en una dirección específica y no te importa perder esa información en la serialización.
	@JsonBackReference
    private Set<Usuario> usuarios;

	private static final long serialVersionUID = 1L;

}
