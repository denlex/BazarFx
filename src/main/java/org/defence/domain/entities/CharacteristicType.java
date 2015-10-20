package org.defence.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public class CharacteristicType implements Serializable {
    private int id;
    private String code;
    private String name;
    private List<Characteristic> characteristics = new ArrayList<>();

    public CharacteristicType() {
    }

    public CharacteristicType(String code, String name) {
        this(code, name, new ArrayList<>());
    }

    public CharacteristicType(String code, String name, List<Characteristic> characteristics) {
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

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
