package org.defence.domain.entities;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class Measurement implements Serializable {
    private int id;
    private String code;
    private String name;
    private String shortName;
    private MeasurementType type;
    private Set<Characteristic> characteristicSet;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MeasurementType getType() {
        return type;
    }

    public void setType(MeasurementType type) {
        this.type = type;
    }

    public Set<Characteristic> getCharacteristicSet() {
        return characteristicSet;
    }

    public void setCharacteristicSet(Set<Characteristic> characteristicSet) {
        this.characteristicSet = characteristicSet;
    }
}
