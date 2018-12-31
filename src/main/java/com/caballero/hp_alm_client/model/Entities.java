package com.caballero.hp_alm_client.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement(name = "Entities")
@XmlAccessorType(XmlAccessType.FIELD)
public class Entities<T extends Entity> {
    @XmlElementRefs(@XmlElementRef(name = "Entity", type = Entity.class))
    private List<T> entities;

    public Entities() {
        this(new ArrayList<T>());
    }

    public Entities(Collection<T> entities) {
        if (entities instanceof List) {
            this.entities = (List<T>) entities;
        } else {
            this.entities = new ArrayList<T>(entities);
        }
    }

    public List<T> entities() {
        return entities;
    }

    public void entities(List<T> entities) {
        this.entities = entities;
    }

    public void addEntity(T entity) {
        entities.add(entity);
    }
}
