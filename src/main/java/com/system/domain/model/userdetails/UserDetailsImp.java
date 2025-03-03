package com.system.domain.model.userdetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.system.domain.model.postgresql.Rol;
import com.system.domain.model.postgresql.Usuario;

import lombok.Data;

//@Data permite que al retornar cualquier objeto UserDetailsImo, también se pueda acceder al atributo usuario
//Validar si es necesario este acceso
@Data
public class UserDetailsImp implements UserDetails {
	
	private Usuario usuario;
	
	public UserDetailsImp(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Rol> rolesLazy = usuario.getRoles();

		// List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		for (Rol rol : rolesLazy) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getRol()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return usuario.getEstatusBloqueo();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return usuario.getEstatus();
	}

}
