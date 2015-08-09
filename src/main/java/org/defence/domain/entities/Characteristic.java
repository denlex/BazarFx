package org.defence.domain.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class Characteristic implements Serializable {
    private int id;
    private String code;
    private String name;
    private Set<CharacteristicsValue> values = new HashSet<CharacteristicsValue>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
