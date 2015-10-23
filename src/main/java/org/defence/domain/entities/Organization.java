package org.defence.domain.entities;

/**
 * Created by root on 22.07.15.
 */
public class Organization {
    private int id;
    private String code;
    private String name;
    private String type;

	public Organization() {
	}

	public Organization(String code, String name, String type) {
		this(null, code, name, type);
	}

	public Organization(Integer id, String code, String name, String type) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
