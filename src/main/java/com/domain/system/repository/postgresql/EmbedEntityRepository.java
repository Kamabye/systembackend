package com.domain.system.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.system.models.postgresql.EmbedEntity;
import com.domain.system.models.postgresql.EmbeddableID;

@Repository
public interface EmbedEntityRepository extends JpaRepository<EmbedEntity, EmbeddableID> {

}
