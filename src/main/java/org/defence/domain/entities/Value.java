package org.defence.domain.entities;

import java.io.Serializable;

/**
 * Created by root on 06.08.15.
 */
public class Value implements Serializable {
    private int id;
    private String value;
//    private Set<CharacteristicKit> characteristicKitSet = new HashSet<CharacteristicKit>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
