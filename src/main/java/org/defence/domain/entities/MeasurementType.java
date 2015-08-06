package org.defence.domain.entities;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class MeasurementType implements Serializable {
    private int id;
    private String name;
    private String code;
    private Set<Measurement> measurementSet;

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

    public Set<Measurement> getMeasurementSet() {
        return measurementSet;
    }

    public void setMeasurementSet(Set<Measurement> measurementSet) {
        this.measurementSet = measurementSet;
    }
}
