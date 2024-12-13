package com.system.domain.models.dto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//@Data
public class DTOGenerico {

	private Map<String, Object> attributes = new HashMap<>();

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DTOGenerico(Object entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(jakarta.persistence.Lob.class) == null) {
                try {
                    if (field.isAnnotationPresent(jakarta.persistence.OneToMany.class) ||
                        field.isAnnotationPresent(jakarta.persistence.ManyToMany.class) ||
                        field.isAnnotationPresent(jakarta.persistence.ManyToOne.class)) {

                        // Handle relationships
                        Object relatedEntities = field.get(entity);
                        if (relatedEntities instanceof List<?>) {
							List<Long> ids = (List<Long>) ((List) relatedEntities).stream()
                                    .map(relatedEntity -> extractId(relatedEntity))
                                    .collect(Collectors.toList());
                            attributes.put(field.getName() + "Ids", ids);
                        } else if (relatedEntities instanceof Set<?>) {
                            Set<Long> ids = ((Set<?>) relatedEntities).stream()
                                    .map(relatedEntity -> extractId(relatedEntity))
                                    .collect(Collectors.toSet());
                            attributes.put(field.getName() + "Ids", ids);
                        } else if (relatedEntities != null) {
                            attributes.put(field.getName() + "Id", extractId(relatedEntities));
                        }
                    } else {
                        // Include all fields excluding @Lob
                        attributes.put(field.getName(), field.get(entity));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	private Long extractId(Object relatedEntity) {
		try {
			Field idField = relatedEntity.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			return (Long) idField.get(relatedEntity);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

}
