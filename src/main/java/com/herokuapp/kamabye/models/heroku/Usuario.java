package com.herokuapp.kamabye.models.heroku;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.herokuapp.kamabye.models.Auditable;

import jakarta.persistence.CascadeType;
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
public class Usuario extends Auditable implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	@Column(name = "idUsuario", updatable = false, nullable = false)
	protected Long id;

	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Integer dia;
	private Integer mes;
	private Integer anio;
	private String sexo;
	@Column(nullable = false)
	private Boolean estatus;

	@Column(nullable = false)
	private Boolean estatusBloqueo;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	// @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
	// CascadeType.MERGE })
	// @JsonManagedReference
	// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinTable(name = "UsuariosRoles", // Nombre de la tabla que se creará
			joinColumns = @JoinColumn(name = "idUsuario", nullable = false, referencedColumnName = "idUsuario"), // Nombre
																													// del
																													// campo
																													// de
																													// la
																													// tabla
			inverseJoinColumns = @JoinColumn(name = "idRol", nullable = false, referencedColumnName = "idRol"), uniqueConstraints = {
					@UniqueConstraint(columnNames = { "idUsuario", "idRol" }) })
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<Rol> rolesLazy = this.getRoles();

		// List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		for (Rol rol : rolesLazy) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getRol()));
		}
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.getEmail();
	}

	@Override
	public boolean isEnabled() {
		return this.getEstatus();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;

	}

	@Override
	public boolean isAccountNonLocked() {
		return this.getEstatusBloqueo();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	private static final long serialVersionUID = 1L;
}
