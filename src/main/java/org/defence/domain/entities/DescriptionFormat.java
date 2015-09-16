package org.defence.domain.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class DescriptionFormat {
    private int id;
    private String code;
    private String name;
    private Set<CharacteristicKit> characteristicKits = new HashSet<>();
    private Set<AssertedName> assertedNames = new HashSet<>();

    public DescriptionFormat() {
    }

	public DescriptionFormat(String code, String name) {
		this(code, name, null, null);
	}

    public DescriptionFormat(String code, String name, Set<CharacteristicKit> characteristicKits, Set<AssertedName> assertedNames) {
        this.code = code;
        this.name = name;
		this.characteristicKits = characteristicKits;
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

    public Set<CharacteristicKit> getCharacteristicKits() {
        return characteristicKits;
    }

    public void setCharacteristicKits(Set<CharacteristicKit> characteristicKits) {
        this.characteristicKits = characteristicKits;
    }

    public Set<AssertedName> getAssertedNames() {
        return assertedNames;
    }

    public void setAssertedNames(Set<AssertedName> catalogDescriptions) {
        this.assertedNames = catalogDescriptions;
    }
}
