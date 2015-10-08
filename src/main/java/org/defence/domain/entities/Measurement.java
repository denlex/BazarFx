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
	private MeasurementType type;

    public Measurement() {
    }

    public Measurement(Integer id, String code, String name, String shortName) {
		this(code, name, shortName);
		this.id = id;
	}

    public Measurement(String code, String name, String shortName) {
		this(code, name, shortName, null);
	}

	public Measurement(String code, String name, String shortName, MeasurementType type) {
		this.code = code;
		this.name = name;
		this.shortName = shortName;
		this.type = type;
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

	public MeasurementType getType() {
		return type;
	}

	public void setType(MeasurementType type) {
		this.type = type;
	}

	@Override
    public int compareTo(Measurement o) {
        return id == o.getId() ? 0 : id > o.getId() ? 1 : -1;
    }
}
