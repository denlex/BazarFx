package org.defence.domain.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class CharacteristicType implements Serializable {
    private int id;
    private String code;
    private String name;
    private Set<Characteristic> characteristics = new HashSet<Characteristic>();

    public CharacteristicType() {
    }

    public CharacteristicType(String code, String name) {
        this(code, name, null);
    }

    public CharacteristicType(String code, String name, Set<Characteristic> characteristics) {
        this.code = code;
        this.name = name;
        this.characteristics = characteristics;
    }

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

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
