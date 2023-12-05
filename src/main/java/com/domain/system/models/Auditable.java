package com.domain.system.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data //Solo usar cuando sea necesario utilizar estos atributos en la aplicacion. Ya que se pueden consultar en la base de datos
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP(3)")
	private Date createdAt;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	//@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)") //Solo para MYSQL, MariaDB
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP(3)")
	//@Column(nullable = false)
	private Date modifiedAt;

	@CreatedBy
	private String createdBy;

	@LastModifiedBy
	private String modifiedBy;

	@PrePersist
	private void onCreate() {
		createdAt = new Date();
		modifiedAt = createdAt; // Se establece updatedAt igual que createdAt al crear la entidad
	}

	@PreUpdate
	private void onUpdate() {
		modifiedAt = new Date(); // Se actualiza updatedAt cuando la entidad se actualiza
	}
}
