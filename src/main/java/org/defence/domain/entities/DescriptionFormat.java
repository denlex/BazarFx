package org.defence.domain.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class DescriptionFormat {
    private int id;
    private String name;
    private String code;
    private Set<CharacteristicKit> characteristicKitSet = new HashSet<CharacteristicKit>();

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<CharacteristicKit> getCharacteristicKitSet() {
        return characteristicKitSet;
    }

    public void setCharacteristicKitSet(Set<CharacteristicKit> characteristicKitSet) {
        this.characteristicKitSet = characteristicKitSet;
    }
}
