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
    private Set<Measurement> measurements = new HashSet<>();

    public Characteristic() {
    }

    public Characteristic(String code, String name) {
        this.code = code;
        this.name = name;
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

    public Set<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<Measurement> measurements) {
        this.measurements = measurements;
    }
}
