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
    private CharacteristicType characteristicType;
    private Measurement measurement;

    private Set<CharacteristicKit> characteristicKitSet = new HashSet<CharacteristicKit>();

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

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public CharacteristicType getCharacteristicType() {
        return characteristicType;
    }

    public void setCharacteristicType(CharacteristicType characteristicType) {
        this.characteristicType = characteristicType;
    }

    public Set<CharacteristicKit> getCharacteristicKitSet() {
        return characteristicKitSet;
    }

    public void setCharacteristicKitSet(Set<CharacteristicKit> characteristicKitSet) {
        this.characteristicKitSet = characteristicKitSet;
    }
}
