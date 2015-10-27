package org.defence.domain.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public class DescriptionFormat {
    private int id;
    private String code;
    private String name;
    private List<Characteristic> characteristics = new ArrayList<>();
    private List<AssertedName> assertedNames = new ArrayList<>();

    public DescriptionFormat() {
    }

	public DescriptionFormat(String code, String name) {
		this(code, name, new LinkedList<>(), new LinkedList<>());
	}

    public DescriptionFormat(String code, String name, List<Characteristic> characteristics, List<AssertedName> assertedNames) {
        this.code = code;
        this.name = name;
		this.characteristics = characteristics;
		this.assertedNames = assertedNames;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(List<Characteristic> characteristics) {
		this.characteristics = characteristics;
	}

	public List<AssertedName> getAssertedNames() {
		return assertedNames;
	}

	public void setAssertedNames(List<AssertedName> assertedNames) {
		this.assertedNames = assertedNames;
	}

	@Override
	public String toString() {
		return String.format("%s. %s", code, name);
	}
}