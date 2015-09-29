package org.defence.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 8/6/15.
 */
public class CharacteristicKit implements Serializable {
    private int id;
    private String name;
    private List<Characteristic> characteristics = new ArrayList<>();

    public CharacteristicKit() {
    }

    public CharacteristicKit(String name) {
        this(name, null);
    }

    public CharacteristicKit(String name, List<Characteristic> characteristics) {
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

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
