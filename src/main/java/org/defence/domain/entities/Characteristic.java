package org.defence.domain.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public class Characteristic implements Comparable<Characteristic> {
    private int id;
    private String code;
    private String name;
    private List<Measurement> measurements = new ArrayList<>();

    public Characteristic() {
    }

    public Characteristic(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Characteristic(String code, String name, List<Measurement> measurements) {
        this(code, name);
        this.measurements = measurements;
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

	public List<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}

	@Override
    public int compareTo(Characteristic o) {
        return this.name.compareToIgnoreCase(o.getName());
    }
}
