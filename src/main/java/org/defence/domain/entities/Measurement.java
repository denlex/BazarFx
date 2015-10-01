package org.defence.domain.entities;

import java.io.Serializable;

/**
 * Created by root on 22.07.15.
 */
public class Measurement implements Serializable, Comparable<Measurement> {
    private int id;
    private String code;
    private String name;
    private String shortName;

    public Measurement() {
    }

    public Measurement(Integer id, String code, String name, String shortName) {
		this(code, name, shortName);
	}

    public Measurement(String code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(Measurement o) {
        return id == o.getId() ? 0 : id > o.getId() ? 1 : -1;
    }
}
