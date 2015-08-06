package org.defence.domain.entities;

import java.io.Serializable;

/**
 * Created by root on 22.07.15.
 */
public class MeasurementType implements Serializable {
    private int id;
    private String name;

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
}
