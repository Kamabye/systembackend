package com.system.domain.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
	
	@CreatedDate
	// private Date createdAtDate;
	// @Column(nullable = false, columnDefinition = "TIMESTAMP(3) WITH TIME ZONE
	// DEFAULT CURRENT_TIMESTAMP(3)")
	// Omitimos nullable, porque la incializacion de la base de datos exige colocar
	// este atributo en los VALUES y no el momento no es requerido hasta definir
	// correctamente la inicializacion
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	// private Date modifiedAtDate;
	// @Column(nullable = false, columnDefinition = "TIMESTAMP(3) WITH TIME ZONE
	// DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)")
	// PostgreSQL no soporta ON UPDATE
	// @Column(columnDefinition = "TIMESTAMP(3) WITH TIME ZONE DEFAULT
	// CURRENT_TIMESTAMP(3)")
	@Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime modifiedAt;
	
	@CreatedBy
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String createdBy;
	
	@LastModifiedBy
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String modifiedBy;
	
}
