package org.defence.domain.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 8/6/15.
 */
public class CharacteristicKit implements Serializable {
    private int id;
    private String name;
    private Set<Characteristic> characteristics = new HashSet<Characteristic>();

    public CharacteristicKit() {
    }

    public CharacteristicKit(String name) {
        this(name, null);
    }

    public CharacteristicKit(String name, Set<Characteristic> characteristics) {
        this.name = name;
        this.characteristics = characteristics;
    }

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

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
