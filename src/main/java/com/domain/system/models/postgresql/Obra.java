package com.domain.system.models.postgresql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.domain.system.models.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Obras")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor // Genera un constructor sin parámetros
//@RequiredArgsConstructor //Genera un constructor por cada parámetro de uso especial final o no nulo
@AllArgsConstructor // Genera un cosntructor para cada parámetro finales o no nulos
public class Obra extends Auditable implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY AutoIncrement MYSQL MariaDB
	@Column(name = "idObra",updatable = false, nullable = false)
	private Long id;
	
	
	private String nombre;
	
	private String compositor;
	
	private String arreglista;
	
	private String letrista;
	
	private BigDecimal precio;
	
	private String genero;
	
	@OneToMany(mappedBy = "obra", cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<Partitura> partituras;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
