package com.domain.system.models.postgresql;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "EmbedEntity")
@Data
public class EmbedEntity {

	@EmbeddedId
	private EmbeddableID embeddableID;

	private Integer cantidad;

}
