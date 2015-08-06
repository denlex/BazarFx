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
    private Set<Characteristic> characteristicSet = new HashSet<Characteristic>();

    public CharacteristicKit() {
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

    public Set<Characteristic> getCharacteristicSet() {
        return characteristicSet;
    }

    public void setCharacteristicSet(Set<Characteristic> characteristicSet) {
        this.characteristicSet = characteristicSet;
    }
}
