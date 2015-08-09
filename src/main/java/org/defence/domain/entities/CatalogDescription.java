package org.defence.domain.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class CatalogDescription implements Serializable {
    private int id;
    private String name;
    private Set<CharacteristicsValue> values = new HashSet<CharacteristicsValue>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CharacteristicsValue> getValues() {
        return values;
    }

    public void setValues(Set<CharacteristicsValue> values) {
        this.values = values;
    }
}
